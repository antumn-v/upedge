package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductAttr;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductAttrDao{

    ProductAttr selectByPrimaryKey(ProductAttr record);

    int deleteByPrimaryKey(ProductAttr record);

    int updateByPrimaryKey(ProductAttr record);

    int updateByPrimaryKeySelective(ProductAttr record);

    int insert(ProductAttr record);

    int insertSelective(ProductAttr record);

    int insertByBatch(List<ProductAttr> list);

    List<ProductAttr> select(Page<ProductAttr> record);

    long count(Page<ProductAttr> record);

}
