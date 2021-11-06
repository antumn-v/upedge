package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.fulfillment.service.FulfillmentService;
import com.upedge.oms.modules.order.dao.OrderTrackingDao;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class OrderTrackingServiceImpl  implements OrderTrackingService {


    @Autowired
    private OrderTrackingDao orderTrackingDao;

    @Autowired
    private FulfillmentService fulfillmentService;

    @Override
    public List<Long> selectOrderIdByState(Integer state) {
        return orderTrackingDao.selectOrderIdByState(state);
    }

    @Override
    public OrderTracking queryOrderTrackingByOrderId(Long id) {
        return orderTrackingDao.queryOrderTrackingByOrderId(id);
    }

    /**
     * 根据OrderId order_tracking_type查询订单
     * @param orderId
     * @param orderType
     * @return
     */
    @Override
    public OrderTracking queryOrderTrackingByOrderId(Long orderId, Integer orderType) {
        OrderTracking orderTracking = new OrderTracking();
        orderTracking.setOrderId(orderId);
        orderTracking.setOrderTrackingType(orderType);
        return orderTrackingDao.queryOrderTracking(orderTracking);

    }

    @Override
    public int insert(OrderTracking orderTracking) {
        return orderTrackingDao.insert(orderTracking);
    }

    @Override
    public void updateOrderTracking(OrderTracking orderTracking) {
        orderTrackingDao.updateOrderTracking(orderTracking);
    }

    @Override
    public void RetransmitLogistics() {
        Page<OrderTracking> page = new Page<>();
        page.setOrderBy("id asc");
        page.setBoundary("id > 0");
        page.setPageSize(500);
        recursivePage(page);
    }

    private void recursivePage(Page<OrderTracking> page) {
       List<OrderTracking> orderTrackingList = orderTrackingDao.recursiveRetransmitLogisticsPage(page);
        while (!CollectionUtils.isEmpty(orderTrackingList)){
            for (OrderTracking orderTracking : orderTrackingList) {
                fulfillmentService.postTrackNumberToStore(orderTracking.getOrderId());
            }
            page.setBoundary("id > " + orderTrackingList.get(orderTrackingList.size() - 1 ).getId());
            orderTrackingList.clear();
            orderTrackingList = orderTrackingDao.recursiveRetransmitLogisticsPage(page);
        }
    }
}
