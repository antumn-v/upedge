package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderDao{

    PurchaseOrder selectByPrimaryKey(PurchaseOrder record);

    int deleteByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    int insertByBatch(List<PurchaseOrder> list);

    List<PurchaseOrder> select(Page<PurchaseOrder> record);

    long count(Page<PurchaseOrder> record);

}
