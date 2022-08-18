package com.upedge.oms.modules.pack.service;

import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrderLabelPrintLogService{

    OrderLabelPrintLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderLabelPrintLog record);

    int updateByPrimaryKeySelective(OrderLabelPrintLog record);

    int insert(OrderLabelPrintLog record);

    int insertSelective(OrderLabelPrintLog record);

    List<OrderLabelPrintLog> select(Page<OrderLabelPrintLog> record);

    long count(Page<OrderLabelPrintLog> record);
}

