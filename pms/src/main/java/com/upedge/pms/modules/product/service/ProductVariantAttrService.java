package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.VariantNameVo;

import java.util.List;

/**
 * @author gx
 */
public interface ProductVariantAttrService{

    List<VariantNameVo> selectNameValueByProductId(Long productId);

    List<ProductVariantAttr> selectByVariantIds(List<Long> variantIds);

    int insertByBatch(List<ProductVariantAttr> productVariantAttrs);

    ProductVariantAttr selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductVariantAttr record);

    int updateByPrimaryKeySelective(ProductVariantAttr record);

    int insert(ProductVariantAttr record);

    int insertSelective(ProductVariantAttr record);

    List<ProductVariantAttr> select(Page<ProductVariantAttr> record);

    long count(Page<ProductVariantAttr> record);
}

