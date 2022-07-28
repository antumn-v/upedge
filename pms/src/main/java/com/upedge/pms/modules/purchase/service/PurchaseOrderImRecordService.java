package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderImRecordService{

    PurchaseOrderImRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrderImRecord record);

    int updateByPrimaryKeySelective(PurchaseOrderImRecord record);

    int insert(PurchaseOrderImRecord record);

    int insertByBatch(List<PurchaseOrderImRecord> records);

    int insertSelective(PurchaseOrderImRecord record);

    List<PurchaseOrderImRecord> select(Page<PurchaseOrderImRecord> record);

    long count(Page<PurchaseOrderImRecord> record);
}

