package com.upedge.oms.modules.cart.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.cart.dao.CartDao;
import com.upedge.oms.modules.cart.entity.Cart;
import com.upedge.oms.modules.cart.request.CartSubmitOrderRequest;
import com.upedge.oms.modules.cart.request.CartSubmitWholesaleRequest;
import com.upedge.oms.modules.cart.request.DelCarts;
import com.upedge.oms.modules.cart.service.CartService;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.stock.dao.StockOrderDao;
import com.upedge.oms.modules.stock.dao.StockOrderItemDao;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderAddressDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CartDao cartDao;
    @Autowired
    StockOrderDao stockOrderDao;

    @Autowired
    StockOrderItemDao stockOrderItemDao;

    @Autowired
    WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    WholesaleOrderAddressDao wholesaleOrderAddressDao;

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    private OrderShippingUnitService orderShippingUnitService;

    //购物车提交备库订单
    @Transactional
    @Override
    public Long cartSubmitStockOrder(CartSubmitOrderRequest request, Session session) {
        List<Cart> carts = request.getCarts();
        if (ListUtils.isEmpty(carts)) {
            return null;
        }
        Long orderId = IdGenerate.nextId();

        BigDecimal amount = BigDecimal.ZERO;

        List<StockOrderItem> orderItems = new ArrayList<>();
        List<Long> cartIds = new ArrayList<>();
        for (Cart cart : carts) {
            StockOrderItem item = new StockOrderItem();
            BeanUtils.copyProperties(cart, item);
            item.setCartId(cart.getId());
            item.setOrderId(orderId);
            amount = amount.add(cart.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            item.setId(null);
            orderItems.add(item);
            if (0 != cart.getId()) {
                cartIds.add(cart.getId());
            }
        }
        stockOrderItemDao.insertByBatch(orderItems);
        if (ListUtils.isNotEmpty(cartIds)) {
            cartDao.updateStateByIds(cartIds, 1);
        }
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(session.getCustomerId()));
        StockOrder order = new StockOrder();
        order.setId(orderId);
        order.setAmount(amount);
        order.setCreateTime(new Date());
        order.setPayState(0);
        order.setRefundState(0);
        order.setSaiheState(0);
        order.setWarehouseId(request.getWarehouseId());
        order.setCustomerId(session.getCustomerId());
        order.setManagerCode(managerCode);
        stockOrderDao.insert(order);
        return orderId;
    }

    /**
     * 购物车提交批发订单，批发订单初始化运输方式
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cartSubmitWholesaleOrder(CartSubmitWholesaleRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Long customerId = session.getCustomerId();
        List<Cart> carts = request.getCarts();

        if (ListUtils.isEmpty(carts)) {
            return false;
        }
        List<Long> variantIds = new ArrayList<>();
        carts.forEach(cart -> {
            variantIds.add(cart.getVariantId());
        });
        ListVariantsRequest listVariantsRequest = new ListVariantsRequest();
        listVariantsRequest.setVariantIds(variantIds);

        Map<Long, Long> variantShipIdMap = new HashMap<>();

        Date date = new Date();
        Long orderId = IdGenerate.nextId();
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        List<WholesaleOrderItem> orderItems = new ArrayList<>();
        List<Long> cartIds = new ArrayList<>();

        for (Cart cart : carts) {
            WholesaleOrderItem item = new WholesaleOrderItem(cart);
            item.setOrderId(orderId);
            item.setId(IdGenerate.nextId());
            item.setShippingId(variantShipIdMap.get(cart.getProductId()));
            orderItems.add(item);
            amount = amount.add(cart.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            weight = weight.add(new BigDecimal(cart.getQuantity()).multiply(cart.getVariantWeight()));
            volume = volume.add(new BigDecimal(cart.getQuantity()).multiply(cart.getVariantVolume()));
            if (0 != cart.getId()) {
                cartIds.add(cart.getId());
            }
        }
        WholesaleOrder order = new WholesaleOrder();
        order.setTotalWeight(weight);
        order.setVolumeWeight(volume);
        order.setProductAmount(amount);
        order.setCustomerId(customerId);
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setId(orderId);
        order.initOrder(order);

        AddressVo addressVo = request.getAddressVo();
        Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID,addressVo.getCountry());
        order.setToAreaId(toAreaId);
        wholesaleOrderDao.insert(order);

        WholesaleOrderAddress orderAddress = new WholesaleOrderAddress();
        BeanUtils.copyProperties(addressVo, orderAddress);
        orderAddress.setOrderId(orderId);
        orderAddress.setId(IdGenerate.nextId());

        wholesaleOrderItemDao.insertByBatch(orderItems);
        wholesaleOrderAddressDao.insert(orderAddress);
        if (ListUtils.isNotEmpty(cartIds)) {
            cartDao.updateStateByIds(cartIds, 1);
        }
        return true;
    }

    @Override
    public List<Cart> selectByIdsAndType(List<Long> ids,
                                         Integer cartType) {
        Session session = UserUtil.getSession(redisTemplate);
        return cartDao.selectByIdsAndType(ids, cartType, session.getCustomerId());
    }

    @Override
    public void updateCartByVariant(VariantDetail variantDetail, String tag) {
        switch (tag) {
            case "price":
                if (null != variantDetail.getUsdPrice()) {
                    cartDao.updatePriceByVariantId(variantDetail.getVariantId(), variantDetail.getUsdPrice());
                }
                break;
            case "weight":
                if (null != variantDetail.getWeight()) {
                    cartDao.updateWeightByVariantId(variantDetail.getVariantId(), variantDetail.getWeight());
                }
                break;
            case "volume":
                if (null != variantDetail.getVolume()) {
                    cartDao.updateVolumeByVariantId(variantDetail.getVariantId(), variantDetail.getVolume());
                }
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Cart record = new Cart();
        record.setId(id);
        return cartDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Cart record) {
        return cartDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Cart record) {
        return cartDao.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCarts(CartAddRequest cartAddRequest) {
        Integer cartType = cartAddRequest.getCartType();
        if (null == cartType) {
            return;
        }
        Date date = new Date();

        Cart cart = cartAddDtoToCart(cartAddRequest, date);

        Cart c = cartDao.selectCart(cart);

        if (null == c) {
            cartDao.insert(cart);
        } else {
            cart.setId(c.getId());
            cartDao.updateQuantity(cart);
        }
    }

    @Override
    public void updatePriceByVariantId(VariantDetail variantDetail) {
        if(null != variantDetail.getUsdPrice()) {
            cartDao.updatePriceByVariantId(variantDetail.getVariantId(), variantDetail.getUsdPrice());
        }
    }

    /**
     *
     */
    public Cart selectByPrimaryKey(Long id) {
        Cart record = new Cart();
        record.setId(id);
        return cartDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(Cart record) {
        return cartDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(Cart record) {
        return cartDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<Cart> select(Page<Cart> record) {
        record.initFromNum();
        return cartDao.select(record);
    }

    /**
     *
     */
    public long count(Page<Cart> record) {
        return cartDao.count(record);
    }

    @Override
    public void delIds(DelCarts delCarts) {
        for (Long cartId : delCarts.getCartIds()) {
            Cart entity=new Cart();
            entity.setId(cartId);
            entity.setState(2);
            entity.setUpdateTime(new Date());
            updateByPrimaryKeySelective(entity);
        }
    }

    public Cart cartAddDtoToCart(CartAddRequest request, Date date) {
        Cart cart = new Cart();
        cart.setCartType(request.getCartType());
        cart.setCustomerId(request.getCustomerId());
        cart.setProductId(request.getProductId());
        cart.setProductTitle(request.getProductTitle());
        cart.setState(0);
        cart.setVariantId(request.getVariantId());
        cart.setVariantName(request.getVariantName());
        cart.setVariantSku(request.getVariantSku());
        cart.setVariantImage(request.getVariantImage());
        cart.setPrice(request.getUsdPrice());
        cart.setVariantWeight(request.getWeight());
        cart.setVariantVolume(request.getVolume());
        cart.setQuantity(request.getQuantity());
        cart.setCreateTime(date);
        cart.setUpdateTime(date);
        return cart;
    }


}