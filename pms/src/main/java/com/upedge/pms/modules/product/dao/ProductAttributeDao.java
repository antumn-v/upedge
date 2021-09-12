package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductAttributeDao{

    ProductAttribute selectByProductId(Long productId);

    ProductAttribute selectByPrimaryKey(ProductAttribute record);

    int deleteByPrimaryKey(ProductAttribute record);

    int updateByPrimaryKey(ProductAttribute record);

    int updateByPrimaryKeySelective(ProductAttribute record);

    int insert(ProductAttribute record);

    int insertSelective(ProductAttribute record);

    int insertByBatch(List<ProductAttribute> list);

    List<ProductAttribute> select(Page<ProductAttribute> record);

    long count(Page<ProductAttribute> record);

}
