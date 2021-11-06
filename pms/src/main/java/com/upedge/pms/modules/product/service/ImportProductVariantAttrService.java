package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductVariantAttr;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductVariantAttrService{

    ImportProductVariantAttr selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportProductVariantAttr record);

    int updateByPrimaryKeySelective(ImportProductVariantAttr record);

    int insert(ImportProductVariantAttr record);

    int insertSelective(ImportProductVariantAttr record);

    List<ImportProductVariantAttr> select(Page<ImportProductVariantAttr> record);

    long count(Page<ImportProductVariantAttr> record);
}

