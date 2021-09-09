package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductLogDao{

    ProductLog selectByPrimaryKey(ProductLog record);

    int deleteByPrimaryKey(ProductLog record);

    int updateByPrimaryKey(ProductLog record);

    int updateByPrimaryKeySelective(ProductLog record);

    int insert(ProductLog record);

    int insertSelective(ProductLog record);

    int insertByBatch(List<ProductLog> list);

    List<ProductLog> select(Page<ProductLog> record);

    long count(Page<ProductLog> record);

}
