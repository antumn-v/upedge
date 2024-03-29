package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.request.StoreProductListRequest;

import java.util.List;

/**
 * @author author
 */
public interface StoreProductAttributeService{

    void refresh(Long productId);

    List<StoreProductAttribute> selectStoreProduct(StoreProductListRequest request);

    StoreProductAttribute saveDefaultCustomProduct(Long customerId);

    Long countStoreProduct(StoreProductListRequest request);

    StoreProductAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(StoreProductAttribute record);

    int updateByPrimaryKeySelective(StoreProductAttribute record);

    int insert(StoreProductAttribute record);

    int insertSelective(StoreProductAttribute record);

    List<StoreProductAttribute> select(Page<StoreProductAttribute> record);

    long count(Page<StoreProductAttribute> record);
}

