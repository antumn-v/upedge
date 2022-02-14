package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.product.RelateDetailVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
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
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AirwallexVo;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderItem record) {
        return orderItemDao.insert(record);
    }

    @Transactional
    @Override
    public BaseResponse orderItemApplyQuote(OrderItemQuoteRequest request, Session session) {

        List<Long> orderIds = request.getOrderIds();
        if (ListUtils.isEmpty(orderIds)) {
            return BaseResponse.failed();
        }
        List<Long> storeVariantIds = orderItemDao.selectStoreVariantIdsByOrderIds(orderIds);
        if (ListUtils.isEmpty(storeVariantIds)) {
            return BaseResponse.failed();
        }
        OrderQuoteApplyRequest orderQuoteApplyRequest = new OrderQuoteApplyRequest();
//        orderQuoteApplyRequest.setOrderId(request.getOrderId());
        orderQuoteApplyRequest.setCustomerId(session.getCustomerId());
        orderQuoteApplyRequest.setUserId(session.getId());
        orderQuoteApplyRequest.setStoreVariantId(storeVariantIds);
        BaseResponse response = pmsFeignClient.orderQuoteApply(orderQuoteApplyRequest);
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            orderItemDao.updateOrderAsQuotingByStoreVariantIds(storeVariantIds);
        }
        return response;
//        Order order = orderService.selectByPrimaryKey(request.getOrderId());
//        if (order.getPayState() != OrderConstant.PAY_STATE_UNPAID
//                || order.getQuoteState() == OrderConstant.QUOTE_STATE_QUOTED) {
//            return BaseResponse.failed("order error");
//        }
//        Page<OrderItem> orderItemPage = new Page<>();
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrderId(request.getOrderId());
//        orderItemPage.setPageSize(-1);
//        orderItemPage.setT(orderItem);
//        List<OrderItem> orderItems = select(orderItemPage);
//        List<Long> itemIds = request.getItemIds();
//        List<Long> storeVariantIds = new ArrayList<>();
//        for (Long itemId : itemIds) {
//            for (OrderItem item : orderItems) {
//                if (itemId.equals(item.getId())){
//                    storeVariantIds.add(item.getStoreVariantId());
//                }
//            }
//        }
//        if (ListUtils.isEmpty(storeVariantIds)){
//            return BaseResponse.failed("item error");
//        }
//        OrderQuoteApplyRequest orderQuoteApplyRequest = new OrderQuoteApplyRequest();
//        orderQuoteApplyRequest.setOrderId(request.getOrderId());
//        orderQuoteApplyRequest.setStoreId(order.getStoreId());
//        orderQuoteApplyRequest.setCustomerId(session.getCustomerId());
//        orderQuoteApplyRequest.setUserId(session.getId());
//        orderQuoteApplyRequest.setStoreVariantId(storeVariantIds);
//        BaseResponse response = pmsFeignClient.orderQuoteApply(orderQuoteApplyRequest);
//        if (response.getCode() == ResultCode.SUCCESS_CODE) {
//            orderItemDao.updateOrderAsQuotingByStoreVariantIds(storeVariantIds);
////            orderItemDao.updateQuoteStateByIds(itemIds, 5);
//        }
//        return response;
    }

    @Transactional
    @Override
    public void updateItemQuoteDetail(CustomerProductQuoteVo customerProductQuoteVo) {
        if (customerProductQuoteVo.getQuoteState() == null){
            return;
        }
        //报价失败的产品
        if (customerProductQuoteVo.getQuoteState() == 2) {
            orderItemDao.cancelItemQuoteDetail(customerProductQuoteVo);
        //报价成功的产品
        }else if (customerProductQuoteVo.getQuoteState() == 1){
            if (null == customerProductQuoteVo.getQuotePrice()) {
                return;
            }
            BigDecimal usdPrice = PriceUtils.cnyToUsdByDefaultRate(customerProductQuoteVo.getQuotePrice());
            customerProductQuoteVo.setUsdPrice(usdPrice);
            orderItemDao.updateItemQuoteDetail(customerProductQuoteVo);
        }else {
            //异常情况直接返回
            return;
        }
        Long storeVariantId = customerProductQuoteVo.getStoreVariantId();
        //查询该产品关联的未支付订单
        List<Long> orderIds = orderItemDao.selectUnpaidOrderIdByStoreVariantId(storeVariantId);
        if (ListUtils.isNotEmpty(orderIds)) {
            //修改订单产品费用
            orderDao.initProductAmountById(orderIds);
            //查询订单中是否包含未报价的产品
            List<Long> partQuoteOrderIds = orderItemDao.selectUnQuoteItemOrderIdByOrderIds(orderIds);
            if (ListUtils.isNotEmpty(partQuoteOrderIds)) {//标记订单为部分报价
                orderDao.updateQuoteStateByIds(partQuoteOrderIds, OrderConstant.QUOTE_STATE_PART_UNQUOTED);
            }
            orderIds.removeAll(partQuoteOrderIds);
            if (ListUtils.isNotEmpty(orderIds)
                    && customerProductQuoteVo.getQuoteState() == 1) {//标记订单未全部报价
                orderDao.updateQuoteStateByIds(orderIds, OrderConstant.QUOTE_STATE_QUOTED);
            }
        }
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
        List<OrderItem> list = null;
        switch (tag) {
            case "price":
                name = "cny_price";
                value = variantDetail.getCnyPrice();
                break;
            case "weight":
                name = "admin_variant_weight";//重量
                value = variantDetail.getWeight();
                break;
            case "volume":
                name = "admin_variant_volume";//体积
                value = variantDetail.getVolume();
                break;
            case "shippingId"://运输模板
                if (null != variantDetail.getProductId() && null != variantDetail.getProductShippingId()) {
                    // 修改订单item
                    orderItemDao.updateShippingIdByAdminProductId(variantDetail.getProductShippingId(), variantDetail.getProductId());
/*                     删除订单表运输信息
                    orderService.delOrderShipInfoByProductId(variantDetail.getProductId());*/

                    orderShippingUnitService.delByProductId(variantDetail.getProductId(), OrderType.NORMAL);
                    list = orderItemDao.selectOrderItemListByProduct(variantDetail.getProductId());
                    // 重新匹配运输规则
                    orderService.matchingShipInfoByProductId(list);
                    return;
                }
                break;
            default:
                return;
        }
        if (null == value) {
            return;
        }
        orderItemDao.updateAdminVariantDetailByVariantId(name, value, variantDetail.getVariantId());
        switch (tag){
            case "price":
                orderItemDao.updateUsdPriceByAdminVariantId(variantDetail.getVariantId(), variantDetail.getUsdPrice());
                break;
            case "volume":
                orderItemDao.updateVolumeByVariantId(variantDetail);
                break;
            default:
                break;
        }
        //未支付订单重新匹配运输方式
        if (!tag.equals("price")) {
            orderShippingUnitService.delByVariantId(variantDetail.getVariantId(), OrderType.NORMAL);
            list = orderItemDao.selectOrderItemListByVariantId(variantDetail.getVariantId());
            orderService.matchingShipInfoByVariantId(list);
        }

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

}