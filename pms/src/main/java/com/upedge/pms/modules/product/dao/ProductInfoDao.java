package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductInfoDao{

    ProductInfo selectByProductId(Long productId);

    ProductInfo selectByPrimaryKey(ProductInfo record);

    int deleteByPrimaryKey(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    int updateByPrimaryKeySelective(ProductInfo record);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    int insertByBatch(List<ProductInfo> list);

    List<ProductInfo> select(Page<ProductInfo> record);

    long count(Page<ProductInfo> record);

}
