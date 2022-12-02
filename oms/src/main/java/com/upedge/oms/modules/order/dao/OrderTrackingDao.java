package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderTracking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderTrackingDao{

    OrderTracking selectByOrderId(Long orderId);

    List<Long> selectOrderIdByState(Integer state);

    OrderTracking selectByPrimaryKey(OrderTracking record);

    int deleteByPrimaryKey(OrderTracking record);

    int updateByPrimaryKey(OrderTracking record);

    int updateByPrimaryKeySelective(OrderTracking record);

    int insert(OrderTracking record);

    int insertSelective(OrderTracking record);

    int insertByBatch(List<OrderTracking> list);

    List<OrderTracking> select(Page<OrderTracking> record);

    long count(Page<OrderTracking> record);

    OrderTracking queryOrderTrackingByOrderId(Long orderId);

    List<OrderTracking> listOrderTrackingByOrderIds(@Param("orderIds") List<Long> orderIds);

    void updateOrderTracking(OrderTracking orderTracking);

    OrderTracking queryOrderTracking(@Param("t") OrderTracking orderTracking);

    List<OrderTracking> recursiveRetransmitLogisticsPage(@Param("page") Page<OrderTracking> page);
}
