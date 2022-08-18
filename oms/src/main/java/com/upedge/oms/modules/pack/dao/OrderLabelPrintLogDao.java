package com.upedge.oms.modules.pack.dao;

import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrderLabelPrintLogDao{

    OrderLabelPrintLog selectByPrimaryKey(OrderLabelPrintLog record);

    int deleteByPrimaryKey(OrderLabelPrintLog record);

    int updateByPrimaryKey(OrderLabelPrintLog record);

    int updateByPrimaryKeySelective(OrderLabelPrintLog record);

    int insert(OrderLabelPrintLog record);

    int insertSelective(OrderLabelPrintLog record);

    int insertByBatch(List<OrderLabelPrintLog> list);

    List<OrderLabelPrintLog> select(Page<OrderLabelPrintLog> record);

    long count(Page<OrderLabelPrintLog> record);

}
