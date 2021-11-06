package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrderOtherFeeRecord;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderOtherFeeRecordDao{

    StoreOrderOtherFeeRecord selectByPrimaryKey(Long storeOrderId);

    int deleteByPrimaryKey(StoreOrderOtherFeeRecord record);

    int updateByPrimaryKey(StoreOrderOtherFeeRecord record);

    int updateByPrimaryKeySelective(StoreOrderOtherFeeRecord record);

    int insert(StoreOrderOtherFeeRecord record);

    int insertSelective(StoreOrderOtherFeeRecord record);

    int insertByBatch(List<StoreOrderOtherFeeRecord> list);

    List<StoreOrderOtherFeeRecord> select(Page<StoreOrderOtherFeeRecord> record);

    long count(Page<StoreOrderOtherFeeRecord> record);

}
