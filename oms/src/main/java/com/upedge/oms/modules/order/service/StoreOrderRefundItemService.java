package com.upedge.oms.modules.order.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderRefundItemService{

    StoreOrderRefundItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreOrderRefundItem record);

    int updateByPrimaryKeySelective(StoreOrderRefundItem record);

    int insert(StoreOrderRefundItem record);

    int insertSelective(StoreOrderRefundItem record);

    List<StoreOrderRefundItem> select(Page<StoreOrderRefundItem> record);

    long count(Page<StoreOrderRefundItem> record);
}

