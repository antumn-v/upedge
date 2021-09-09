package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductLogService{

    ProductLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductLog record);

    int updateByPrimaryKeySelective(ProductLog record);

    int insert(ProductLog record);

    int insertSelective(ProductLog record);

    List<ProductLog> select(Page<ProductLog> record);

    long count(Page<ProductLog> record);
}

