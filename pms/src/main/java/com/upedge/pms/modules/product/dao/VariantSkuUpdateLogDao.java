package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.VariantSkuUpdateLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface VariantSkuUpdateLogDao{

    VariantSkuUpdateLog selectByPrimaryKey(VariantSkuUpdateLog record);

    int deleteByPrimaryKey(VariantSkuUpdateLog record);

    int updateByPrimaryKey(VariantSkuUpdateLog record);

    int updateByPrimaryKeySelective(VariantSkuUpdateLog record);

    int insert(VariantSkuUpdateLog record);

    int insertSelective(VariantSkuUpdateLog record);

    int insertByBatch(List<VariantSkuUpdateLog> list);

    List<VariantSkuUpdateLog> select(Page<VariantSkuUpdateLog> record);

    long count(Page<VariantSkuUpdateLog> record);

}
