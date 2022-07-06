package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.VariantSkuUpdateLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface VariantSkuUpdateLogService{

    VariantSkuUpdateLog selectByPrimaryKey(Long variantId);

    int deleteByPrimaryKey(Long variantId);

    int updateByPrimaryKey(VariantSkuUpdateLog record);

    int updateByPrimaryKeySelective(VariantSkuUpdateLog record);

    int insert(VariantSkuUpdateLog record);

    int insertSelective(VariantSkuUpdateLog record);

    List<VariantSkuUpdateLog> select(Page<VariantSkuUpdateLog> record);

    long count(Page<VariantSkuUpdateLog> record);
}

