package com.upedge.pms.modules.product.service;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ImportProductVariant;
import com.upedge.pms.modules.product.vo.ImportVariantVo;

import java.util.List;

/**
 * @author author
 */
public interface ImportProductVariantService{

    List<ImportVariantVo> selectByProductId(Long productId);

    ImportProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ImportProductVariant record);

    int updateByPrimaryKeySelective(ImportProductVariant record);

    int insert(ImportProductVariant record);

    int insertSelective(ImportProductVariant record);

    List<ImportProductVariant> select(Page<ImportProductVariant> record);

    long count(Page<ImportProductVariant> record);
}

