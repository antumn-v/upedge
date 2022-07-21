package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderItemService{

    List<PurchaseOrderItem> selectByOrderIds(List<Long> orderIds);

    PurchaseOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKeySelective(PurchaseOrderItem record);

    int insert(PurchaseOrderItem record);

    int insertByBatch(List<PurchaseOrderItem> records);

    int insertSelective(PurchaseOrderItem record);

    List<PurchaseOrderItem> select(Page<PurchaseOrderItem> record);

    long count(Page<PurchaseOrderItem> record);
}

