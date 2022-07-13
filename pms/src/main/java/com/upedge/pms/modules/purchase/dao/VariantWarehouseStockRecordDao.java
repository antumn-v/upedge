package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockRecordDao{

    VariantWarehouseStockRecord selectByPrimaryKey(VariantWarehouseStockRecord record);

    int deleteByPrimaryKey(VariantWarehouseStockRecord record);

    int updateByPrimaryKey(VariantWarehouseStockRecord record);

    int updateByPrimaryKeySelective(VariantWarehouseStockRecord record);

    int insert(VariantWarehouseStockRecord record);

    int insertSelective(VariantWarehouseStockRecord record);

    int insertByBatch(List<VariantWarehouseStockRecord> list);

    List<VariantWarehouseStockRecord> select(Page<VariantWarehouseStockRecord> record);

    long count(Page<VariantWarehouseStockRecord> record);

}
