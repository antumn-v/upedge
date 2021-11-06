package com.upedge.oms.modules.fulfillment.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.fulfillment.entity.StoreOrderFulfillment;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderFulfillmentDao{

    StoreOrderFulfillment selectByPrimaryKey(StoreOrderFulfillment record);

    int deleteByPrimaryKey(StoreOrderFulfillment record);

    int updateByPrimaryKey(StoreOrderFulfillment record);

    int updateByPrimaryKeySelective(StoreOrderFulfillment record);

    int insert(StoreOrderFulfillment record);

    int insertSelective(StoreOrderFulfillment record);

    int insertByBatch(List<StoreOrderFulfillment> list);

    List<StoreOrderFulfillment> select(Page<StoreOrderFulfillment> record);

    long count(Page<StoreOrderFulfillment> record);

}
