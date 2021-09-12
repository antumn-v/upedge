package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductVariant;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductVariantDao{

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
