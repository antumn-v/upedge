package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantStockListRequest;
import com.upedge.pms.modules.purchase.vo.VariantWarehouseStockRecordVo;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockRecordDao{

    List<VariantWarehouseStockRecordVo> selectVariantStockRecordVos(VariantStockListRequest request);

    long countVariantStockRecordVos(VariantStockListRequest request);

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
