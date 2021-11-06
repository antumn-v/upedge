package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderRefund;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderRefundService{

    StockOrderRefund selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOrderRefund record);

    int updateByPrimaryKeySelective(StockOrderRefund record);

    int insert(StockOrderRefund record);

    int insertSelective(StockOrderRefund record);

    List<StockOrderRefund> select(Page<StockOrderRefund> record);

    long count(Page<StockOrderRefund> record);
}

