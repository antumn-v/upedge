package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductAttributeService{

    StoreProductAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductAttribute record);

    int updateByPrimaryKeySelective(StoreProductAttribute record);

    int insert(StoreProductAttribute record);

    int insertSelective(StoreProductAttribute record);

    List<StoreProductAttribute> select(Page<StoreProductAttribute> record);

    long count(Page<StoreProductAttribute> record);
}

