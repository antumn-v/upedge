package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PurchaseOrderImRecordDao{

    PurchaseOrderImRecord selectByPrimaryKey(PurchaseOrderImRecord record);

    int deleteByPrimaryKey(PurchaseOrderImRecord record);

    int updateByPrimaryKey(PurchaseOrderImRecord record);

    int updateByPrimaryKeySelective(PurchaseOrderImRecord record);

    int insert(PurchaseOrderImRecord record);

    int insertSelective(PurchaseOrderImRecord record);

    int insertByBatch(List<PurchaseOrderImRecord> list);

    List<PurchaseOrderImRecord> select(Page<PurchaseOrderImRecord> record);

    long count(Page<PurchaseOrderImRecord> record);

}
