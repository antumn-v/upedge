package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductImgService{

    ProductImg selectByPrimaryKey(Long id);

    int insertByBatch(List<ProductImg> productImgs);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductImg record);

    int updateByPrimaryKeySelective(ProductImg record);

    int insert(ProductImg record);

    int insertSelective(ProductImg record);

    List<ProductImg> select(Page<ProductImg> record);

    long count(Page<ProductImg> record);
}

