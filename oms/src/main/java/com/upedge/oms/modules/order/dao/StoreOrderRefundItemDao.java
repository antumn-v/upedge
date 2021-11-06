package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderRefundItemDao{

    StoreOrderRefundItem selectByPrimaryKey(StoreOrderRefundItem record);

    void updateStoreOrderItemId(@Param("storeOrderRefundId") Long storeOrderRefundId,
                                @Param("storeOrderId") Long storeOrderId);

    int deleteByPrimaryKey(StoreOrderRefundItem record);

    int updateByPrimaryKey(StoreOrderRefundItem record);

    int updateByPrimaryKeySelective(StoreOrderRefundItem record);

    int insert(StoreOrderRefundItem record);

    int insertSelective(StoreOrderRefundItem record);

    int insertByBatch(List<StoreOrderRefundItem> list);

    List<StoreOrderRefundItem> select(Page<StoreOrderRefundItem> record);

    long count(Page<StoreOrderRefundItem> record);

}
