package com.upedge.pms.modules.purchase.service;

import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface VariantStockExImRecordService{

    VariantStockExImRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(VariantStockExImRecord record);

    int updateByPrimaryKeySelective(VariantStockExImRecord record);

    int insert(VariantStockExImRecord record);

    int insertSelective(VariantStockExImRecord record);

    List<VariantStockExImRecord> select(Page<VariantStockExImRecord> record);

    long count(Page<VariantStockExImRecord> record);
}

