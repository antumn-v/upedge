package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductDescription;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductDescriptionService{

    ImportProductDescription selectByProductId(Long productId);

    ImportProductDescription selectByPrimaryKey(Long id);

    int updateByProductId(ImportProductDescription importProductDescription);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportProductDescription record);

    int updateByPrimaryKeySelective(ImportProductDescription record);

    int insert(ImportProductDescription record);

    int insertSelective(ImportProductDescription record);

    List<ImportProductDescription> select(Page<ImportProductDescription> record);

    long count(Page<ImportProductDescription> record);
}

