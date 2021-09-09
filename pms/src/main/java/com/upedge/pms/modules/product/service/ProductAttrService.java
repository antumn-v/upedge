package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductAttr;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductAttrService{

    ProductAttr selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductAttr record);

    int updateByPrimaryKeySelective(ProductAttr record);

    int insert(ProductAttr record);

    int insertSelective(ProductAttr record);

    List<ProductAttr> select(Page<ProductAttr> record);

    long count(Page<ProductAttr> record);
}

