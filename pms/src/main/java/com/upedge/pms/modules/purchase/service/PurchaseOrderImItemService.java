package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImItem;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderImItemService{

    PurchaseOrderImItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrderImItem record);

    int updateByPrimaryKeySelective(PurchaseOrderImItem record);

    int insert(PurchaseOrderImItem record);

    int insertSelective(PurchaseOrderImItem record);

    List<PurchaseOrderImItem> select(Page<PurchaseOrderImItem> record);

    long count(Page<PurchaseOrderImItem> record);
}

