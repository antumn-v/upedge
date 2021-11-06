package com.upedge.oms.modules.statistics.service;

import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;

/**
 * 订单退款每日统计
 */
public interface OrderDailyRefundCountService {


    /**
     * 订单退款每日统计  根据客户进行分组统计
     * @param refundDailyCountRequest
     */
     void OrderDailyRefundCount(OrderRefundDailyCountRequest refundDailyCountRequest);

}
