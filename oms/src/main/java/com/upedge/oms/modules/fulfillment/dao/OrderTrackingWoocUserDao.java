package com.upedge.oms.modules.fulfillment.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.fulfillment.entity.OrderTrackingWoocUser;

import java.util.List;

/**
 * @author author
 */
public interface OrderTrackingWoocUserDao{

    OrderTrackingWoocUser selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(OrderTrackingWoocUser record);

    int updateByPrimaryKey(OrderTrackingWoocUser record);

    int updateByPrimaryKeySelective(OrderTrackingWoocUser record);

    int insert(OrderTrackingWoocUser record);

    int insertSelective(OrderTrackingWoocUser record);

    int insertByBatch(List<OrderTrackingWoocUser> list);

    List<OrderTrackingWoocUser> select(Page<OrderTrackingWoocUser> record);

    long count(Page<OrderTrackingWoocUser> record);

}
