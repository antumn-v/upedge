package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.VariantNameVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface ProductVariantAttrService{

    void updateByBatch(@Param("list")List<ProductVariantAttr> productVariantAttrList);

    List<ProductVariantAttr> selectByProductId(Long productId);

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

