package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrderRefund;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderRefundDao{

    List<StoreOrderRefund> selectByStoreOrderId(Long storeOrderId);

    StoreOrderRefund selectByPrimaryKey(StoreOrderRefund record);

    int deleteByPrimaryKey(StoreOrderRefund record);

    int updateByPrimaryKey(StoreOrderRefund record);

    int updateByPrimaryKeySelective(StoreOrderRefund record);

    int insert(StoreOrderRefund record);

    int insertSelective(StoreOrderRefund record);

    int insertByBatch(List<StoreOrderRefund> list);

    List<StoreOrderRefund> select(Page<StoreOrderRefund> record);

    long count(Page<StoreOrderRefund> record);

}
