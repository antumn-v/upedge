package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockRecordService{

    VariantWarehouseStockRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(VariantWarehouseStockRecord record);

    int updateByPrimaryKeySelective(VariantWarehouseStockRecord record);

    int insert(VariantWarehouseStockRecord record);

    int insertSelective(VariantWarehouseStockRecord record);

    List<VariantWarehouseStockRecord> select(Page<VariantWarehouseStockRecord> record);

    long count(Page<VariantWarehouseStockRecord> record);
}

