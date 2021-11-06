package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrderAddress;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderAddressDao{

    StoreOrderAddress selectByStoreOrderId(Long storeOrderId);

    StoreOrderAddress selectByPrimaryKey(StoreOrderAddress record);

    int deleteByPrimaryKey(StoreOrderAddress record);

    int updateByPrimaryKey(StoreOrderAddress record);

    int updateByPrimaryKeySelective(StoreOrderAddress record);

    int insert(StoreOrderAddress record);

    int insertSelective(StoreOrderAddress record);

    int insertByBatch(List<StoreOrderAddress> list);

    List<StoreOrderAddress> select(Page<StoreOrderAddress> record);

    long count(Page<StoreOrderAddress> record);

}
