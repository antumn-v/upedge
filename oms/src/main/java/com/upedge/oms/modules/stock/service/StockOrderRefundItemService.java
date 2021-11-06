package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderRefundItemService{

    StockOrderRefundItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StockOrderRefundItem record);

    int updateByPrimaryKeySelective(StockOrderRefundItem record);

    int insert(StockOrderRefundItem record);

    int insertSelective(StockOrderRefundItem record);

    List<StockOrderRefundItem> select(Page<StockOrderRefundItem> record);

    long count(Page<StockOrderRefundItem> record);
}

