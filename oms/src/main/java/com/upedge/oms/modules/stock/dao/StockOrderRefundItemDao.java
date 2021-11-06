package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface StockOrderRefundItemDao{

    StockOrderRefundItem selectByPrimaryKey(StockOrderRefundItem record);

    int deleteByPrimaryKey(StockOrderRefundItem record);

    int updateByPrimaryKey(StockOrderRefundItem record);

    int updateByPrimaryKeySelective(StockOrderRefundItem record);

    int insert(StockOrderRefundItem record);

    int insertSelective(StockOrderRefundItem record);

    int insertByBatch(List<StockOrderRefundItem> list);

    List<StockOrderRefundItem> select(Page<StockOrderRefundItem> record);

    long count(Page<StockOrderRefundItem> record);

    List<StockOrderRefundItem> listStockOrderRefundItem(Long stockRefundId);

}
