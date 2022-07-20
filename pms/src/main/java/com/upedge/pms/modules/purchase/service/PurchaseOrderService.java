package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderService{

    PurchaseOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    List<PurchaseOrder> select(Page<PurchaseOrder> record);

    long count(Page<PurchaseOrder> record);
}

