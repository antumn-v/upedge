package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.oms.order.OrderStockClearRequest;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.order.dto.OrderItemPurchaseAdviceDto;
import com.upedge.common.model.order.vo.OrderItemPurchaseAdviceVo;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.pms.request.VariantStockRestoreLockQuantityRequest;
import com.upedge.common.model.pms.vo.PurchaseAdviceItemVo;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.product.RelateDetailVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.dao.StoreOrderItemDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.request.AirwallexRequest;
import com.upedge.oms.modules.order.request.OrderItemQuoteRequest;
import com.upedge.oms.modules.order.request.OrderItemUpdateQuantityRequest;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AirwallexVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.order.vo.OrderStoreVariantIdsVo;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    StoreOrderItemDao storeOrderItemDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    private OrderShippingUnitService orderShippingUnitService;

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderItem record = new OrderItem();
        record.setId(id);
        return orderItemDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderItem record) {
        return orderItemDao.insert(record);
    }

    @Override
    public int insertByBatch(List<OrderItem> orderItems) {
        if (ListUtils.isEmpty(orderItems)){
            return 0;
        }
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQuoteScale() == null || orderItem.getQuoteScale() == 0){
                orderItem.setQuoteScale(1);
            }
        }
        return orderItemDao.insertByBatch(orderItems);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderItem record) {
        return orderItemDao.insert(record);
    }

    @Override
    public int updateLockedQuantityClear(OrderStockClearRequest request) {
        return orderItemDao.updateLockedQuantityClear(request);
    }

    @Override
    public List<Long> selectOutStockOrderIdsByVariantId(Long variantId) {
        return orderItemDao.selectOutStockOrderIdsByVariantId(variantId);
    }

    @Override
    public List<Long> selectItemAdminVariantIds(OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto) {
        return orderItemDao.selectItemAdminVariantIds(orderItemPurchaseAdviceDto);
    }

    @Override
    public List<OrderItem> selectByOrderIds(List<Long> orderIds) {
        if (ListUtils.isNotEmpty(orderIds)) {
            return orderItemDao.selectByOrderIds(orderIds);
        }
        return new ArrayList<>();
    }

    @Override
    public int batchUpdatePickedQuantity(List<OrderItem> orderItems) {
        if (ListUtils.isNotEmpty(orderItems)) {
            return orderItemDao.batchUpdatePickedQuantity(orderItems);
        }
        return 0;
    }

    @Override
    public Integer selectCountQuantityByOrderId(Long orderId) {
        return orderItemDao.selectCountQuantityByOrderId(orderId);
    }

    @Override
    public List<Long> selectOrderIdsByStoreOrderItemIds(List<Long> storeOrderItemIds) {
        if (ListUtils.isNotEmpty(storeOrderItemIds)) {
            return orderItemDao.selectOrderIdsByStoreOrderItemIds(storeOrderItemIds);
        }
        return null;
    }

    @Override
    public int updateQuantityByStoreOrderItemId(Long storeOrderItemId, Integer quantity) {
        if (null != storeOrderItemId
                && null != quantity) {
            orderItemDao.updateQuantityByStoreOrderItemId(storeOrderItemId, quantity);
        }
        return 0;
    }

    @Override
    public List<OrderItem> selectByOrderId(Long orderId) {
        if (null == orderId) {
            return null;
        }
        return orderItemDao.selectItemByOrderId(orderId);
    }

    @Override
    public int updateImageNameByStoreVariantId(OrderItemUpdateImageNameRequest request) {
        if (StringUtils.isBlank(request.getName())
                && StringUtils.isBlank(request.getImage())) {
            return 0;
        }
        if (request.getStoreVariantId() == null) {
            return 0;
        }
        return orderItemDao.updateImageNameByStoreVariantId(request);
    }

    @Transactional
    @Override
    public BaseResponse orderItemApplyQuote(OrderItemQuoteRequest request, Session session) {
        Long itemId = request.getItemId();
        List<Long> orderIds = request.getOrderIds();
        if (ListUtils.isEmpty(orderIds)
                && itemId == null) {
            return BaseResponse.failed();
        }
        List<Long> storeVariantIds = new ArrayList<>();
        if (ListUtils.isNotEmpty(orderIds)) {
            storeVariantIds.addAll(orderItemDao.selectStoreVariantIdsByOrderIds(orderIds));
        }
        if (itemId != null) {
            OrderItem orderItem = orderItemDao.selectByPrimaryKey(itemId);
            if (orderItem != null
                    && orderItem.getQuoteState() == 0) {
                storeVariantIds.add(orderItem.getStoreVariantId());
            }
        }
        if (ListUtils.isEmpty(storeVariantIds)) {
            return BaseResponse.failed();
        }
        OrderQuoteApplyRequest orderQuoteApplyRequest = new OrderQuoteApplyRequest();
        orderQuoteApplyRequest.setCustomerId(session.getCustomerId());
        orderQuoteApplyRequest.setUserId(session.getId());
        orderQuoteApplyRequest.setStoreVariantId(storeVariantIds);
        BaseResponse response = pmsFeignClient.orderQuoteApply(orderQuoteApplyRequest);
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            orderItemDao.updateOrderAsQuotingByStoreVariantIds(storeVariantIds);
        }
        return response;
    }

    @Transactional
    @Override
    public void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) {
        if (customerProductQuoteVo.getQuoteState() == null) {
            return;
        }
        //报价失败的产品
        if (customerProductQuoteVo.getQuoteState() == 0) {
            orderItemDao.cancelItemQuoteDetail(customerProductQuoteVo);
            //报价成功的产品
        } else if (customerProductQuoteVo.getQuoteState() == 1) {
            if (null == customerProductQuoteVo.getQuotePrice()) {
                return;
            }
            BigDecimal usdPrice = PriceUtils.cnyToUsdByDefaultRate(customerProductQuoteVo.getQuotePrice());
            customerProductQuoteVo.setUsdPrice(usdPrice);
            orderItemDao.updateItemQuoteDetail(customerProductQuoteVo);
        } else {
            //异常情况直接返回
            return;
        }
        Long storeVariantId = customerProductQuoteVo.getStoreVariantId();
        //查询该产品关联的未支付订单
        List<Long> orderIds = orderItemDao.selectUnpaidOrderIdByStoreVariantId(storeVariantId);
        if (ListUtils.isEmpty(orderIds)) {
            return;
        }
        //查询订单中是否包含报价中的产品
        List<Long> quotingOrders = orderItemDao.selectOrderIdsByOrderIdsAndQuoteState(orderIds, OrderItem.QUOTE_STATE_QUOTING);
        if (ListUtils.isNotEmpty(quotingOrders)) {//标记订单为部分报价
            orderDao.updateQuoteStateByIds(quotingOrders, OrderConstant.QUOTE_STATE_QUOTING);
        }
        orderIds.removeAll(quotingOrders);
        //查询订单中是否包含未报价或报价失败的产品
        if (ListUtils.isEmpty(orderIds)) {
            return;
        }
        List<Long> partQuoteOrderIds = orderItemDao.selectUnQuoteItemOrderIdByOrderIds(orderIds);
        if (ListUtils.isNotEmpty(partQuoteOrderIds)) {
            orderDao.updateQuoteStateByIds(partQuoteOrderIds, OrderConstant.QUOTE_STATE_PART_UNQUOTED);
        }
        orderIds.removeAll(partQuoteOrderIds);
        //全部报价的订单修改订单产品费用并匹配运输方式
        if (ListUtils.isNotEmpty(orderIds)
                && customerProductQuoteVo.getQuoteState() == 1) {//标记订单未全部报价
            orderDao.updateQuoteStateByIds(orderIds, OrderConstant.QUOTE_STATE_QUOTED);
            //修改订单产品费用
            orderDao.initProductAmountById(orderIds);
            //修改订单运输方式
            for (Long orderId : orderIds) {
                orderService.matchShipRule(orderId);
            }
        }
    }

    @Override
    public void updatePaidOrderItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) throws CustomerException {
        if (customerProductQuoteVo == null || customerProductQuoteVo.getQuoteState() == 0) {
            return;
        }
        Long newVariantId = customerProductQuoteVo.getVariantId();
        List<Long> orderIds = new ArrayList<>();
        Long storeVariantId = customerProductQuoteVo.getStoreVariantId();
        List<OrderItem> orderItems = orderItemDao.selectPaidItemLockedQuantityByStoreVariantId(storeVariantId);
        Integer totalLockedQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            Long variantId = orderItem.getAdminVariantId();
            if (variantId.equals(newVariantId)){
                return;
            }
            totalLockedQuantity += orderItem.getLockedQuantity();

            if (!orderIds.contains(orderItem.getOrderId())){
                orderIds.add(orderItem.getOrderId());
            }
        }

        if (0 != totalLockedQuantity) {
            VariantStockRestoreLockQuantityRequest request = new VariantStockRestoreLockQuantityRequest();
            request.setLockQuantity(totalLockedQuantity);
            request.setWarehouseStock("CNHZ");
            request.setVariantId(orderItems.get(0).getAdminVariantId());
            BaseResponse response = pmsFeignClient.restoreLockQuantity(request);
            if (response.getCode() != ResultCode.SUCCESS_CODE) {
                throw new CustomerException("数据异常");
            }
        }

        orderItemDao.updatePaidItemQuoteDetail(customerProductQuoteVo);
        OrderItemQuantityDto orderItemQuantityDto = new OrderItemQuantityDto();
        orderItemQuantityDto.setOrderIds(orderIds);

        List<OrderItemQuantityVo> orderItemQuantityVos = orderDao.selectOrderItemQuantities(orderItemQuantityDto);
        pmsFeignClient.orderCheckStock(orderItemQuantityVos);
    }

    @Transactional
    @Override
    public void updateSplitVariantItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) throws CustomerException {

        if (customerProductQuoteVo == null
                || customerProductQuoteVo.getStoreParentVariantId().equals(0L)
                || customerProductQuoteVo.getStoreParentVariantId() == null
                || customerProductQuoteVo.getQuoteState() == null) {
            return;
        }
        //0=新增  1=修改 2=父体新增拆分变体
        Integer type = 0;
        //判断父体变体是否已报价  若已报价则无法操作
        Long storeParentVariantId = customerProductQuoteVo.getStoreParentVariantId();
        String key = "storeVariant:split:update:item:parent:" + storeParentVariantId;
        boolean b = RedisUtil.lock(redisTemplate, key, 10L, 30 * 1000L);
        if (!b) {
            throw new CustomerException(key + "---->获取锁失败");
        }
        Long storeVariantId = customerProductQuoteVo.getStoreVariantId();
        List<Long> splitVariantIds = (List<Long>) redisTemplate.opsForHash().get(RedisKey.HASH_STORE_SPLIT_VARIANT, String.valueOf(storeParentVariantId));
        if (ListUtils.isEmpty(splitVariantIds)
                || !splitVariantIds.contains(storeVariantId)) {
            return;
        }
        OrderItem quoteItem = orderItemDao.selectByStoreVariantIdAndQuoteState(storeParentVariantId, OrderItem.QUOTE_STATE_QUOTED);
        if (null != quoteItem) {
            return;
        }
        OrderItem upedgeItem = orderItemDao.selectByStoreVariantIdAndQuoteState(storeParentVariantId, OrderItem.QUOTE_STATE_UPEDGE);
        if (null != upedgeItem) {
            return;
        }
        //判断父产品是否有订单
        List<Long> orderIds = orderItemDao.selectUnpaidOrderIdByStoreVariantId(storeParentVariantId);
        if (ListUtils.isEmpty(orderIds)) {
            //判断是否有产品需要更新信息
            quoteItem = orderItemDao.selectByStoreVariantIdAndQuoteState(storeVariantId, null);
            if (null != quoteItem) {
                if (customerProductQuoteVo.getQuoteState() == 0) {
                    orderItemDao.cancelItemQuoteDetail(customerProductQuoteVo);
                    //报价成功的产品
                } else if (customerProductQuoteVo.getQuoteState() == 1) {
                    if (null == customerProductQuoteVo.getQuotePrice()) {
                        return;
                    }
                    BigDecimal usdPrice = PriceUtils.cnyToUsdByDefaultRate(customerProductQuoteVo.getQuotePrice());
                    customerProductQuoteVo.setUsdPrice(usdPrice);
                    orderItemDao.updateItemQuoteDetail(customerProductQuoteVo);
                } else {
                    //异常情况直接返回
                    return;
                }
            }

            //判断该组合产品的订单里是否有遗漏此产品的
            orderIds = new ArrayList<>();
            List<OrderStoreVariantIdsVo> orderStoreVariantIdsVos = orderItemDao.selectOrderStoreVariantIdsByStoreVariantIds(splitVariantIds);
            for (OrderStoreVariantIdsVo orderStoreVariantIdsVo : orderStoreVariantIdsVos) {
                Long orderId = orderStoreVariantIdsVo.getOrderId();
                List<Long> storeVariantIds = orderStoreVariantIdsVo.getStoreVariantIds();
                if (storeVariantIds.contains(storeVariantId)) {
                    continue;
                }
                OrderItem orderItem = orderItemDao.selectByOrderIdAndStoreVariantId(orderId, storeVariantIds.get(0));
                //更新item对象，重新插入数据库
                orderItem.quoteProductToItem(customerProductQuoteVo);
                orderItem.setStoreVariantImage(customerProductQuoteVo.getStoreVariantImage());
                orderItem.setStoreVariantName(customerProductQuoteVo.getStoreVariantName());
                orderItem.setStoreVariantSku(customerProductQuoteVo.getStoreVariantSku());
                orderItem.setStoreVariantId(customerProductQuoteVo.getStoreVariantId());
                orderItem.setQuoteState(OrderItem.QUOTE_STATE_QUOTED);
                orderItem.setId(IdGenerate.nextId());
                insert(orderItem);
                orderIds.add(orderId);
            }
        } else {
            //父变体有订单，删除订单里的副产品，插入子产品
            variantFirstSplit(orderIds, customerProductQuoteVo, storeParentVariantId, true);
        }
        if (ListUtils.isEmpty(orderIds)) {
            return;
        }
        //查询订单中是否包含报价中的产品
        List<Long> quotingOrders = orderItemDao.selectOrderIdsByOrderIdsAndQuoteState(orderIds, OrderItem.QUOTE_STATE_QUOTING);
        if (ListUtils.isNotEmpty(quotingOrders)) {//标记订单为部分报价
            orderDao.updateQuoteStateByIds(quotingOrders, OrderConstant.QUOTE_STATE_QUOTING);
        }
        orderIds.removeAll(quotingOrders);
        if (ListUtils.isEmpty(orderIds)) {
            return;
        }
        //查询订单中是否包含未报价或报价失败的产品
        List<Long> partQuoteOrderIds = orderItemDao.selectUnQuoteItemOrderIdByOrderIds(orderIds);
        if (ListUtils.isNotEmpty(partQuoteOrderIds)) {
            orderDao.updateQuoteStateByIds(partQuoteOrderIds, OrderConstant.QUOTE_STATE_PART_UNQUOTED);
        }
        orderIds.removeAll(partQuoteOrderIds);
        //全部报价的订单修改订单产品费用并匹配运输方式
        if (ListUtils.isNotEmpty(orderIds)
                && customerProductQuoteVo.getQuoteState() == 1) {//标记订单未全部报价
            orderDao.updateQuoteStateByIds(orderIds, OrderConstant.QUOTE_STATE_QUOTED);
            //修改订单产品费用
            orderDao.initProductAmountById(orderIds);
            //修改订单运输方式
            for (Long orderId : orderIds) {
                orderService.matchShipRule(orderId);
            }
        }
        RedisUtil.unLock(redisTemplate, key);
    }

    @Override
    public List<ItemDischargeQuantityVo> selectDischargeQuantityByPaymentId(Long paymentId) {
        return orderItemDao.selectDischargeQuantityByPaymentId(paymentId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addOrderItemFromStoreOrder(Long orderId, Long storeOrderId, RelateDetailVo relateDetailVo) {
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (order.getPayState() > 0 || order.getOrderType() == 1) {
            return false;
        }
        List<RelateVariantVo> newVariantVos = relateDetailVo.getRelateVariantVos();
        Integer scale = relateDetailVo.getScale();
        Integer variantType = relateDetailVo.getVariantType();
        Long storeVariantId = relateDetailVo.getStoreVariantId();
        for (RelateVariantVo variantVo : newVariantVos) {
            OrderItem orderItem = new OrderItem(variantVo);
            StoreOrderItem storeOrderItem = storeOrderItemDao.selectByStoreVariantId(storeOrderId, storeVariantId);
            if (null == storeOrderItem || storeOrderItem.getState() == 1) {
                continue;
            }
            BeanUtils.copyProperties(storeOrderItem, orderItem);
            orderItem.setOrderId(orderId);
            orderItem.setStoreOrderItemId(storeOrderItem.getId());
            orderItem.setQuantity(scale * storeOrderItem.getQuantity());
            orderItem.setDischargeQuantity(0);
            orderItem.setItemType(variantType);
            orderItem.setId(IdGenerate.nextId());
            orderItemDao.insert(orderItem);

            storeOrderItem.setState(1);

            storeOrderItemDao.updateByPrimaryKeySelective(storeOrderItem);
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAdminVariantByVariantId(VariantDetail variantDetail, String tag) {
        String name = null;
        Object value = null;
        List<Long> list = null;
        switch (tag) {
            case "weight":
                name = "admin_variant_weight";//重量
                value = variantDetail.getWeight();
                orderItemDao.updateAdminVariantDetailByVariantId(name, value, variantDetail.getVariantId());
                list = orderItemDao.selectOrderItemListByVariantId(variantDetail.getVariantId());
                break;
            case "volume":
                orderItemDao.updateVolumeByVariantId(variantDetail);
                return;
            case "shippingId"://运输模板
                if (null == variantDetail.getProductId()
                        || null == variantDetail.getProductShippingId()) {
                    return;
                }
                // 修改订单item
                orderItemDao.updateShippingIdByAdminProductId(variantDetail.getProductShippingId(), variantDetail.getProductId());
                list = orderItemDao.selectOrderItemListByProduct(variantDetail.getProductId());
                break;
            default:
                return;
        }

        if (ListUtils.isNotEmpty(list)) {
            orderShippingUnitService.deleteByOrderIds(list, OrderType.NORMAL);
            orderService.orderInitShipDetailByIds(list);
        }

        return;

//        //未支付订单重新匹配运输方式
//        if (!tag.equals("price")) {
//
//        }

    }

    @Override
    public int updateQuantity(OrderItemUpdateQuantityRequest request, Long id) {
        Integer quantity = request.getQuantity();
        OrderItem orderItem = orderItemDao.selectByPrimaryKey(id);
        Order order = orderDao.selectByPrimaryKey(orderItem.getOrderId());

        if (order.getPayState() != 0) {
            return 0;
        }

        if (quantity == 0) {
            Integer totalQuantity = orderItemDao.selectCountQuantityByOrderId(orderItem.getOrderId());
            if (totalQuantity - orderItem.getQuantity() <= 0) {
                quantity = 1;
            }
        }

        if (quantity.equals(orderItem.getQuantity())) {
            return 1;
        }
        orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setQuantity(quantity);
        return orderItemDao.updateByPrimaryKeySelective(orderItem);

    }

    @Override
    public int updateAdminVariantByStoreVariantId(Long storeVariantId, RelateVariantVo relateVariantVo) {
        return orderItemDao.updateAdminVariantByStoreVariantId(storeVariantId, relateVariantVo);
    }

    @Override
    public int saveItemStockRecord(Long paymentId, Long customerId) {
        return 0;
    }

    /**
     *
     */
    public OrderItem selectByPrimaryKey(Long id) {
        return orderItemDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(OrderItem record) {
        return orderItemDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(OrderItem record) {
        return orderItemDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<OrderItem> select(Page<OrderItem> record) {
        record.initFromNum();
        return orderItemDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderItem> record) {
        return orderItemDao.count(record);
    }

    @Override
    public List<AirwallexVo> airwallex(AirwallexRequest airwallexRequest) {
        return orderItemDao.airwallex(airwallexRequest);
    }

    @Override
    public int updateCustomOrderItemPrice(Long variantId, BigDecimal cnyPrice, BigDecimal usdPrice) {
        return orderItemDao.updateCustomOrderItemPrice(variantId, cnyPrice, usdPrice);
    }

    @Override
    public List<VariantPreSaleQuantity> selectVariantPreSaleQuantity(List<Long> variantIds) {
        if (ListUtils.isEmpty(variantIds)) {
            return new ArrayList<>();
        }
        return orderItemDao.selectVariantPreSaleQuantity(variantIds);
    }

    @Override
    public OrderItemPurchaseAdviceVo selectUnStockOrderItems() {
        List<OrderItem> orderItems = orderItemDao.selectUnStockOrderItems();
        if (ListUtils.isEmpty(orderItems)) {
            return null;
        }
        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            PurchaseAdviceItemVo purchaseAdviceItemVo = new PurchaseAdviceItemVo();
            purchaseAdviceItemVo.setVariantId(orderItem.getAdminVariantId());
            purchaseAdviceItemVo.setOrderQuantity(orderItem.getQuantity());
            purchaseAdviceItemVo.setCustomerId(orderItem.getCustomerId());
            purchaseAdviceItemVos.add(purchaseAdviceItemVo);
        }
//        long total = orderItemDao.countUnStockOrderItems(request);
        OrderItemPurchaseAdviceVo orderItemPurchaseAdviceVo = new OrderItemPurchaseAdviceVo();
        orderItemPurchaseAdviceVo.setPurchaseAdviceItemVos(purchaseAdviceItemVos);
//        orderItemPurchaseAdviceVo.setTotal(total);
        return orderItemPurchaseAdviceVo;
    }


    public List<Long> variantFirstSplit(List<Long> orderIds, CustomerProductQuoteVo customerProductQuoteVo, Long storeParentVariantId, boolean shouldDelete) {
        //若父变体拥有订单，代表是新增的拆分变体，删除订单下的父体变体，将拆分变体插入到订单里面
        Page<OrderItem> page = new Page<>();
        page.setT(new OrderItem());
        page.getT().setStoreVariantId(storeParentVariantId);
        page.setPageSize(-1);
        page.initFromNum();
        List<OrderItem> orderItems = select(page);
        for (OrderItem orderItem : orderItems) {
            if (!orderIds.contains(orderItem.getOrderId())) {
                continue;
            }
            if (shouldDelete) {
                //删除旧的item
                Long oldId = orderItem.getId();
                deleteByPrimaryKey(oldId);
            }
            //更新item对象，重新插入数据库
            orderItem.quoteProductToItem(customerProductQuoteVo);
            orderItem.setStoreVariantImage(customerProductQuoteVo.getStoreVariantImage());
            orderItem.setStoreVariantName(customerProductQuoteVo.getStoreVariantName());
            orderItem.setStoreVariantSku(customerProductQuoteVo.getStoreVariantSku());
            orderItem.setStoreVariantId(customerProductQuoteVo.getStoreVariantId());
            orderItem.setQuoteState(OrderItem.QUOTE_STATE_QUOTED);
            orderItem.setId(IdGenerate.nextId());
            insert(orderItem);
        }
        return orderIds;
    }

}