package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderActionLog;

import java.util.List;

/**
 * @author author
 */
public interface OrderActionLogDao{

    OrderActionLog selectByPrimaryKey(OrderActionLog record);

    int deleteByPrimaryKey(OrderActionLog record);

    int updateByPrimaryKey(OrderActionLog record);

    int updateByPrimaryKeySelective(OrderActionLog record);

    int insert(OrderActionLog record);

    int insertSelective(OrderActionLog record);

    int insertByBatch(List<OrderActionLog> list);

    List<OrderActionLog> select(Page<OrderActionLog> record);

    long count(Page<OrderActionLog> record);

}
