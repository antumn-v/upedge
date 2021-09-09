package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductAttribute;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductAttributeService{

    ProductAttribute selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductAttribute record);

    int updateByPrimaryKeySelective(ProductAttribute record);

    int insert(ProductAttribute record);

    int insertSelective(ProductAttribute record);

    List<ProductAttribute> select(Page<ProductAttribute> record);

    long count(Page<ProductAttribute> record);
}

