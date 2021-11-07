package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.vo.SaiheSkuVo;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface ProductVariantDao{

    ProductVariant selectBySku(String sku);

    Map<String, BigDecimal> selectVariantPriceRange(Long productId);

    List<SaiheSkuVo> listSaiheSkuVo(@Param("ids") List<Long> ids);

    List<ProductVariant> selectByProductId(Long productId);

    ProductVariant selectByPrimaryKey(ProductVariant record);

    int deleteByPrimaryKey(ProductVariant record);

    int updateByPrimaryKey(ProductVariant record);

    int updateByPrimaryKeySelective(ProductVariant record);

    int insert(ProductVariant record);

    int insertSelective(ProductVariant record);

    int insertByBatch(List<ProductVariant> list);

    List<ProductVariant> select(Page<ProductVariant> record);

    long count(Page<ProductVariant> record);

}