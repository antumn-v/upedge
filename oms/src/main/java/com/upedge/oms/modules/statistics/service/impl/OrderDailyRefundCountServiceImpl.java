package com.upedge.oms.modules.statistics.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.service.OrderRefundService;
import com.upedge.oms.modules.pack.service.PackageOrderInfoService;
import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import com.upedge.oms.modules.statistics.entity.OrderDailyRefundCount;
import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.statistics.service.OrderDailyRefundCountService;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;
import com.upedge.oms.modules.stock.service.StockOrderRefundService;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundService;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.service.SaiheService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单退款每日统计
 *
 * @param
 */
@Slf4j
@Service
public class OrderDailyRefundCountServiceImpl implements OrderDailyRefundCountService {

    /**
     * 批发订单退款service
     */
    @Autowired
    private WholesaleRefundService wholesaleRefundService;


    /**
     * 普通订单退款service
     */
    @Autowired
    private OrderRefundService orderRefundService;

    /**
     * 库存订单退款service
     */
    @Autowired
    private StockOrderRefundService stockOrderRefundService;


    /**
     * orderDailyPayCountService 按天统计订单service
     */
    @Autowired
    private OrderDailyPayCountService orderDailyPayCountService;

    /**
     * 订单包裹service
     */
    @Autowired
    private PackageOrderInfoService packageOrderInfoService;

    /**
     *
     */
    @Autowired
    private UmsFeignClient umsFeignClient;
    /**
     * 订单退款每日统计  根据客户进行分组统计
     *
     * @param refundDailyCountRequest
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void OrderDailyRefundCount(OrderRefundDailyCountRequest refundDailyCountRequest) {
        Long refundId = refundDailyCountRequest.getRefundId();
        Date refundTime = refundDailyCountRequest.getRefundTime();
        Integer orderType = refundDailyCountRequest.getOrderType();
        //订单类型  备库 = 1，普通 = 2，批发 = 3
        OrderDailyRefundCount orderDailyRefundCount = null;
        switch (orderType) {
            case OrderType.STOCK:
                orderDailyRefundCount = updateCustomerStockOrderDailyRefundCount(refundId, refundTime, orderType);
                break;
            case OrderType.NORMAL:
                orderDailyRefundCount = updateCustomerNormalOrderDailyRefundCount(refundId, refundTime, orderType);
                break;
            case OrderType.WHOLESALE:
                orderDailyRefundCount = updateCustomerWholesaleOrderDailyRefundCount(refundId, refundTime, orderType);
                break;
            default:
                break;
        }
        if (null == orderDailyRefundCount) {
            return;
        }
        if (StringUtils.isBlank(orderDailyRefundCount.getManagerCode())){
            orderDailyRefundCount.setManagerCode("system");
        }
        orderDailyRefundCount.setId(IdGenerate.nextId());
        OrderDailyPayCount refundCount = orderDailyPayCountService.selectRefundCountByCustomerOrderTypeAndDate(orderDailyRefundCount.getCustomerId(), refundTime, orderType);
        if (refundCount != null) {
            orderDailyPayCountService.updateRefundCount(orderDailyRefundCount);
        } else {
            int inset = orderDailyPayCountService.insertRefundCount(orderDailyRefundCount);
            if (inset == 0) {
                orderDailyPayCountService.updateRefundCount(orderDailyRefundCount);
            }
        }
    }

    /**
     * 批发订单每日退款订单处理
     *
     * @param refundId   refund 表id
     * @param refundTime 退款时间
     * @param orderType  订单类型   OrderType
     * @return
     */
    private OrderDailyRefundCount updateCustomerWholesaleOrderDailyRefundCount(Long refundId, Date refundTime, Integer orderType) {
        OrderDailyRefundCount orderDailyRefundCount = new OrderDailyRefundCount();
        WholesaleRefund wholesaleRefund = wholesaleRefundService.selectByPrimaryKey(refundId);
        orderDailyRefundCount.setCustomerId(wholesaleRefund.getCustomerId());
        orderDailyRefundCount.setOrderType(orderType);
        orderDailyRefundCount.setPayTime(refundTime);
        orderDailyRefundCount.setRefundAmount(wholesaleRefund.getRefundAmount());
        // 退款订单发货状态 0:未发货 1:已发货
        if (wholesaleRefund.getTrackingState().equals(0)) {
            orderDailyRefundCount.setUnShippedRefundCount(1);
            orderDailyRefundCount.setUnShippedRefundAmount(wholesaleRefund.getRefundAmount());
            orderDailyRefundCount.setManagerCode(wholesaleRefund.getManagerCode());
        } else {
            orderDailyRefundCount.setShippedRefundCount(1);
            orderDailyRefundCount.setShippedRefundAmount(wholesaleRefund.getRefundAmount());
            Integer orderSourceId = getOrderSourceIdBySaihe(wholesaleRefund.getOrderId());
            String managerCode = null;
            if (null != orderSourceId){
                 managerCode= getManagerCodeByOrderSourceId(orderSourceId);
            }
            orderDailyRefundCount.setManagerCode(managerCode == null?wholesaleRefund.getManagerCode():managerCode);
        }
        return orderDailyRefundCount;
    }

    /**
     * 普通订单每日退款订单处理
     *
     * @param refundId   refund 表id
     * @param refundTime 退款时间
     * @param orderType  订单类型   OrderType
     * @return
     */
    private OrderDailyRefundCount updateCustomerNormalOrderDailyRefundCount(Long refundId, Date refundTime, Integer orderType) {
        OrderDailyRefundCount orderDailyRefundCount = new OrderDailyRefundCount();
        OrderRefund orderRefund = orderRefundService.selectByPrimaryKey(refundId);
        orderDailyRefundCount.setCustomerId(orderRefund.getCustomerId());
        orderDailyRefundCount.setOrderType(orderType);
        orderDailyRefundCount.setPayTime(refundTime);
        orderDailyRefundCount.setRefundAmount(orderRefund.getRefundAmount());
        // 退款订单发货状态 0:未发货 1:已发货
        if (orderRefund.getTrackingState().equals(0)) {
            orderDailyRefundCount.setUnShippedRefundCount(1);
            orderDailyRefundCount.setUnShippedRefundAmount(orderRefund.getRefundAmount());
            orderDailyRefundCount.setManagerCode(orderRefund.getManagerCode());
        } else {
            orderDailyRefundCount.setShippedRefundCount(1);
            orderDailyRefundCount.setShippedRefundAmount(orderRefund.getRefundAmount());
            Integer orderSourceId = getOrderSourceIdBySaihe(orderRefund.getOrderId());
            String managerCode = null;
            if (null != orderSourceId){
                managerCode= getManagerCodeByOrderSourceId(orderSourceId);
            }
            orderDailyRefundCount.setManagerCode(managerCode == null?orderRefund.getManagerCode():managerCode);
        }
        return orderDailyRefundCount;
    }

    /**
     * 库存订单每日退款订单处理
     *
     * @param refundId   refund 表id
     * @param refundTime 退款时间
     * @param orderType  订单类型   OrderType
     * @return
     */
    private OrderDailyRefundCount updateCustomerStockOrderDailyRefundCount(Long refundId, Date refundTime, Integer orderType) {
        OrderDailyRefundCount orderDailyPayCount = new OrderDailyRefundCount();
        StockOrderRefund stockOrderRefund = stockOrderRefundService.selectByPrimaryKey(refundId);
        orderDailyPayCount.setCustomerId(stockOrderRefund.getCustomerId());
        orderDailyPayCount.setPayTime(refundTime);
        orderDailyPayCount.setOrderType(orderType);
        orderDailyPayCount.setManagerCode(stockOrderRefund.getManagerCode());
        orderDailyPayCount.setRefundAmount(stockOrderRefund.getAmount());
        return orderDailyPayCount;
    }

    /**
     * 获取赛盒渠道id
     */
    private Integer getOrderSourceIdBySaihe(Long orderId) {
        ApiGetOrderResponse apiGetOrderResponse2 = SaiheService.getOrderByClientOrderCode(orderId.toString());
        if (apiGetOrderResponse2.getGetOrdersResult().getStatus().equals("OK")) {
            List<ApiOrderInfo> list = apiGetOrderResponse2.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
            if (list != null && list.size() > 0) {
                //获取赛盒单号
                Integer orderSourceId = apiGetOrderResponse2.getGetOrdersResult().getOrderInfoList().
                        getOrderInfoList().get(0).getOrderSourceID();
                return orderSourceId;
            }
        }
        return null;
    }

    /**
     * 根据赛盒渠道id寻找managerCode
     */
    private String getManagerCodeByOrderSourceId(Integer orderSourceId) {
        String managerCode = null;
        BaseResponse baseResponse = umsFeignClient.getManagerByOrderSourceId(Long.parseLong(orderSourceId.toString()));
        if (baseResponse.getCode()  == ResultCode.SUCCESS_CODE){
            managerCode  = JSON.toJSONString(baseResponse.getData());
        }
        return managerCode;
    }
}
