package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductVariantService{

    int insertByBatch(List<ProductVariant> productVariants);

    ProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductVariant record);

    int updateByPrimaryKeySelective(ProductVariant record);

    int insert(ProductVariant record);

    int insertSelective(ProductVariant record);

    List<ProductVariant> select(Page<ProductVariant> record);

    long count(Page<ProductVariant> record);
}

