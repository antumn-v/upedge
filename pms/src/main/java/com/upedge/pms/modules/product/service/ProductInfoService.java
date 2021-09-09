package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductInfoService{

    ProductInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductInfo record);

    int updateByPrimaryKeySelective(ProductInfo record);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> select(Page<ProductInfo> record);

    long count(Page<ProductInfo> record);
}

