package com.upedge.oms.modules.order.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.StoreProductSalesVo;
import com.upedge.oms.modules.order.entity.StoreOrderItem;

import java.util.List;

/**
 * @author author
 */
public interface StoreOrderItemService{

    List<StoreProductSalesVo> selectStoreProductSales();

    List<Long> selectStoreOrderIdByStoreVariantIdAndState(Long storeVariantId,
                                                          Integer state);

    StoreOrderItem selectByStoreVariantId(Long storeOrderId,
                                          Long storeVariantId);

    StoreOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreOrderItem record);

    int updateByPrimaryKeySelective(StoreOrderItem record);

    int insert(StoreOrderItem record);
    List<StoreOrderItem> insertByBatch(List<StoreOrderItem> storeOrderItems);

    int insertSelective(StoreOrderItem record);

    List<StoreOrderItem> select(Page<StoreOrderItem> record);

    long count(Page<StoreOrderItem> record);
}

