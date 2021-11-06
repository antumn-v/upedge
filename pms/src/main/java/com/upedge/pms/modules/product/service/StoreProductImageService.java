package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductImage;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductImageService{

    StoreProductImage selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductImage record);

    int updateByPrimaryKeySelective(StoreProductImage record);

    int insert(StoreProductImage record);

    int insertSelective(StoreProductImage record);

    List<StoreProductImage> select(Page<StoreProductImage> record);

    long count(Page<StoreProductImage> record);
}

