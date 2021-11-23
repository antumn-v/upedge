package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductDescription;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductDescriptionDao{

    int updateByProductId(ImportProductDescription importProductDescription);

    ImportProductDescription selectByProductId(Long productId);

    ImportProductDescription selectByPrimaryKey(ImportProductDescription record);

    int deleteByPrimaryKey(ImportProductDescription record);

    int deleteDescByProductId(Long productId);

    int updateByPrimaryKey(ImportProductDescription record);

    int updateByPrimaryKeySelective(ImportProductDescription record);

    int insert(ImportProductDescription record);

    int insertSelective(ImportProductDescription record);

    int insertByBatch(List<ImportProductDescription> list);

    List<ImportProductDescription> select(Page<ImportProductDescription> record);

    long count(Page<ImportProductDescription> record);

}
