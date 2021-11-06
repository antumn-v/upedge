package com.upedge.oms.modules.order.service;

import com.upedge.oms.modules.order.entity.OrderTracking;

import java.util.List;

public interface OrderTrackingService {

    List<Long> selectOrderIdByState(Integer state);

    OrderTracking queryOrderTrackingByOrderId(Long id);

    /**
     * 根据OrderId order_tracking_type查询订单物流
     * @param orderId
     * @param orderType
     * @return
     */
    OrderTracking queryOrderTrackingByOrderId(Long orderId, Integer orderType);


    int insert(OrderTracking orderTracking);

    void updateOrderTracking(OrderTracking orderTracking);

    void RetransmitLogistics();

}
