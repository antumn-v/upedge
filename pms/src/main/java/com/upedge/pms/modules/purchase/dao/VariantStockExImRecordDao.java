package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface VariantStockExImRecordDao{

    VariantStockExImRecord selectByPrimaryKey(VariantStockExImRecord record);

    int deleteByPrimaryKey(VariantStockExImRecord record);

    int updateByPrimaryKey(VariantStockExImRecord record);

    int updateByPrimaryKeySelective(VariantStockExImRecord record);

    int insert(VariantStockExImRecord record);

    int insertSelective(VariantStockExImRecord record);

    int insertByBatch(List<VariantStockExImRecord> list);

    List<VariantStockExImRecord> select(Page<VariantStockExImRecord> record);

    long count(Page<VariantStockExImRecord> record);

}
