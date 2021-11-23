package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface ProductVariantAttrDao{

    List<ProductVariantAttr> selectByProductId(Long productId);
    List<ProductVariantAttr> selectByVariantId(Long variantId);

    List<ProductVariantAttr> selectByVariantIds(@Param("variantIds") List<Long> variantIds);

    ProductVariantAttr selectByPrimaryKey(ProductVariantAttr record);

    int deleteByPrimaryKey(ProductVariantAttr record);

    int updateByPrimaryKey(ProductVariantAttr record);

    int updateByPrimaryKeySelective(ProductVariantAttr record);

    int insert(ProductVariantAttr record);

    int insertSelective(ProductVariantAttr record);

    int insertByBatch(List<ProductVariantAttr> list);

    List<ProductVariantAttr> select(Page<ProductVariantAttr> record);

    long count(Page<ProductVariantAttr> record);

}
