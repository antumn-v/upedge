package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.VariantAttrVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author gx
 */
public interface ProductVariantService{


    List<ProductVariant> getProductVariantList(List<Long> variantIds, Map<String, VariantAttrVo> attrMap, Map<String, Set<String>> attrValSet, Map<Long, ProductVariant> productVariantMap);

    List<ProductVariant> selectByProductId(Long productId);

    int insertByBatch(List<ProductVariant> productVariants);

    ProductVariant selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductVariant record);

    int updateByPrimaryKeySelective(ProductVariant record);

    int insert(ProductVariant record);

    int insertSelective(ProductVariant record);

    List<ProductVariant> select(Page<ProductVariant> record);

    long count(Page<ProductVariant> record);
}

