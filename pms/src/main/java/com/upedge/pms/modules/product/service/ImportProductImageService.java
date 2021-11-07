package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductImage;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductImageService{

    List<ImportProductImage> selectByProductId(Long productId);

    ImportProductImage selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportProductImage record);

    int updateByPrimaryKeySelective(ImportProductImage record);

    int insert(ImportProductImage record);

    int insertSelective(ImportProductImage record);

    List<ImportProductImage> select(Page<ImportProductImage> record);

    long count(Page<ImportProductImage> record);
}

