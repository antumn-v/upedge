package com.upedge.oms.modules.fulfillment.service;

import com.upedge.oms.modules.order.entity.OrderTracking;

public interface FulfillmentService {

    /**
     * 回传物流单号到店铺
     * @param id
     * @return
     */
    boolean postTrackNumberToStore(Long id);

    /**
     * 更细物流信息
     * @param orderTracking
     * @return
     */
    boolean updateOrderTracking(OrderTracking orderTracking);
}
