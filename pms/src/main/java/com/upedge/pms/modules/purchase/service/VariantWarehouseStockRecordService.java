package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordListRequest;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockRecordService{

    BaseResponse variantStockRecordList(VariantWarehouseStockRecordListRequest request);

    VariantWarehouseStockRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(VariantWarehouseStockRecord record);

    int updateByPrimaryKeySelective(VariantWarehouseStockRecord record);

    int insert(VariantWarehouseStockRecord record);

    int insertByBatch(List<VariantWarehouseStockRecord> records);

    int insertSelective(VariantWarehouseStockRecord record);

    List<VariantWarehouseStockRecord> select(Page<VariantWarehouseStockRecord> record);

    long count(Page<VariantWarehouseStockRecord> record);
}

