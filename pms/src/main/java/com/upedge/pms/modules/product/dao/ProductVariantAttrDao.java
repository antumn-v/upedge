package com.upedge.pms.modules.product.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.pms.modules.product.vo.VariantNameVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface ProductVariantAttrDao{

    void updateByBatch(@Param("list")List<ProductVariantAttr> productVariantAttrList);

    /**
     * 根据产品ID查找启用变体的属性集合
     * @param productId
     * @return
     */
    List<VariantNameVo> selectNameValueByProductId(Long productId);

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

    void deleteByVariantId(Long variantId);

}
