package com.upedge.oms.modules.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
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
        order.setAdminState(0);
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
//        BaseResponse response = pmsFeignClient.listVariantDetailByIds(listVariantsRequest);
//        if (ResultCode.FAIL_CODE == response.getCode()
//                || null == response.getData()) {
//            return false;
//        }
        Map<Long, Long> variantShipIdMap = new HashMap<>();
//        List<VariantDetail> variantDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(VariantDetail.class);
//        variantDetails.forEach(variantDetail -> {
//            variantShipIdMap.put(variantDetail.getProductId(), variantDetail.getProductShippingId());
//        });
        Date date = new Date();
        Long orderId = IdGenerate.nextId();
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        List<WholesaleOrderItem> orderItems = new ArrayList<>();
        List<Long> cartIds = new ArrayList<>();
        Collection<String> strings = new ArrayList<>();

        for (Cart cart : carts) {
            WholesaleOrderItem item = new WholesaleOrderItem(cart);
            item.setOrderId(orderId);
            item.setId(IdGenerate.nextId());
            item.setShippingId(variantShipIdMap.get(cart.getProductId()));
            orderItems.add(item);
            strings.add(RedisKey.SHIPPING_METHODS + item.getShippingId());
            amount = amount.add(cart.getPrice().multiply(new BigDecimal(cart.getQuantity())));
            weight = weight.add(new BigDecimal(cart.getQuantity()).multiply(cart.getVariantWeight()));
            volume = volume.add(new BigDecimal(cart.getQuantity()).multiply(cart.getVariantVolume()));
            if (0 != cart.getId()) {
                cartIds.add(cart.getId());
            }
        }
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(session.getCustomerId()));
        WholesaleOrder order = new WholesaleOrder();
        order.setProductAmount(amount);
        order.setCustomerId(customerId);
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setId(orderId);
        order.setOrderType(0);
        order.setManagerCode(managerCode);
        order.setOrderStatus(0);

        AddressVo addressVo = request.getAddressVo();
        Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID,addressVo.getCountry());
        order.setToAreaId(toAreaId);
        wholesaleOrderDao.insert(order);

        WholesaleOrderAddress orderAddress = new WholesaleOrderAddress();
        BeanUtils.copyProperties(addressVo, orderAddress);
        orderAddress.setOrderId(orderId);
        orderAddress.setId(IdGenerate.nextId());
//        AreaSelectRequest areaSelectRequest = new AreaSelectRequest();
//        areaSelectRequest.setEnName(addressVo.getCountry());
//        BaseResponse baseResponse = tmsFeignClient.areaSelect(areaSelectRequest);
//        Long shippingUnitId = null;
//        if (null != baseResponse.getData()) {
//            AreaVo areaVo = JSONObject.parseObject(JSON.toJSON(baseResponse.getData()).toString()).toJavaObject(AreaVo.class);
//            order.setToAreaId(areaVo.getId());
//        }
//            Set<Object> shipMethodIds = redisTemplate.opsForSet().union(strings);
//            if (ListUtils.isNotEmpty(shipMethodIds)) {
//                Set<Long> methodIds = new HashSet<>();
//                shipMethodIds.forEach(shipMethodId -> {
//                    methodIds.add((Long) shipMethodId);
//                });
//                ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
//                searchRequest.setToAreaId(areaVo.getId());
//                searchRequest.setWeight(weight);
//                searchRequest.setVolumeWeight(volume);
//                searchRequest.setMethodIds(methodIds);
//                ShipMethodSearchResponse searchResponse = tmsFeignClient.shipSearch(searchRequest);
//
//                if (searchResponse.getCode() == ResultCode.SUCCESS_CODE) {
//                    List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(searchResponse.getData())).toJavaList(ShipDetail.class);
//                    if (ListUtils.isNotEmpty(shipDetails)) {
//                        ShipDetail shipDetail = shipDetails.get(0);
//                        order.setShipMethodId(shipDetail.getMethodId());
//                        order.setShipPrice(shipDetail.getPrice());
//                        order.setTotalWeight(shipDetail.getWeight());
//                        shippingUnitId = shipDetails.get(0).getShippingUtilId();
//                    }
//                }
//            }
//        }
//        if (shippingUnitId != null){
//            // 删除原来的unit  并插入新的冗余
//            //OrderShippingUnitVo OrderShippingUnitVo = orderShippingUnitService.selectByOrderId(id,OrderType.NORMAL);
//            orderShippingUnitService.delByOrderId(order.getId(), OrderType.WHOLESALE);
//            BaseResponse shippingUnitResponse = tmsFeignClient.unitInfo(shippingUnitId);
//            if (shippingUnitResponse.getCode() == ResultCode.SUCCESS_CODE && shippingUnitResponse.getData() != null){
//                ShippingUnit shippingUnit = JSON.parseObject(JSON.toJSONString(shippingUnitResponse.getData()), ShippingUnit.class);
//                OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
//                BeanUtils.copyProperties(shippingUnit,orderShippingUnit);
//                orderShippingUnit.setOrderId(order.getId());
//                orderShippingUnit.setOrderType(OrderType.WHOLESALE);
//                orderShippingUnit.setId(IdGenerate.nextId());
//                orderShippingUnit.setShipUnitId(shippingUnit.getId());
//                orderShippingUnit.setCreateTime(new Date());
//                orderShippingUnitService.insert(orderShippingUnit);
//            }
//        }
        wholesaleOrderItemDao.insertByBatch(orderItems);
        wholesaleOrderAddressDao.insert(orderAddress);
        if (ListUtils.isNotEmpty(cartIds)) {
            cartDao.updateStateByIds(cartIds, 1);
        }
        wholesaleOrderService.orderInitShipDetail(orderId);
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
                if (null != variantDetail.getPrice()) {
                    cartDao.updatePriceByVariantId(variantDetail.getVariantId(), variantDetail.getPrice());
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
        if(null != variantDetail.getPrice()) {
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
        cart.setPrice(request.getPrice());
        cart.setVariantWeight(request.getWeight());
        cart.setVariantVolume(request.getVolume());
        cart.setQuantity(request.getQuantity());
        cart.setCreateTime(date);
        cart.setUpdateTime(date);
        return cart;
    }


}