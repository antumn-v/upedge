package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pick.vo.OrderPickWaveInfoVo;

import java.util.List;

/**
 * @author gx
 */
public interface OrderLabelPrintLogDao{


    OrderPickWaveInfoVo selectTheLatestPackLabel(Long packNo);

    List<OrderLabelPrintLog> selectByPackNo(Long packNo);

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
