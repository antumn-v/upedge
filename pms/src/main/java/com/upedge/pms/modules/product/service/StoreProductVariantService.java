package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductVariant;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductVariantService{

    StoreProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductVariant record);

    int updateByPrimaryKeySelective(StoreProductVariant record);

    int insert(StoreProductVariant record);

    int insertSelective(StoreProductVariant record);

    List<StoreProductVariant> select(Page<StoreProductVariant> record);

    long count(Page<StoreProductVariant> record);
}

