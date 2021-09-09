package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductDao{

    Product selectByProductSku(String productSku);

    Product selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Product record);

    int updateByPrimaryKey(Product record);

    int updateByPrimaryKeySelective(Product record);

    int insert(Product record);

    int insertSelective(Product record);

    int insertByBatch(List<Product> list);

    List<Product> select(Page<Product> record);

    long count(Page<Product> record);

}
