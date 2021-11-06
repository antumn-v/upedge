package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductImage;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductImageDao{

    StoreProductImage selectByPrimaryKey(StoreProductImage record);

    int deleteByPrimaryKey(StoreProductImage record);

    int updateByPrimaryKey(StoreProductImage record);

    int updateByPrimaryKeySelective(StoreProductImage record);

    int insert(StoreProductImage record);

    int insertSelective(StoreProductImage record);

    int insertByBatch(List<StoreProductImage> list);

    List<StoreProductImage> select(Page<StoreProductImage> record);

    long count(Page<StoreProductImage> record);

}
