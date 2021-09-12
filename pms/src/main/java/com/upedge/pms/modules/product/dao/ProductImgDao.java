package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ProductImgDao{

    List<ProductImg> selectByProductId(Long productId);

    ProductImg selectByPrimaryKey(ProductImg record);

    int deleteByPrimaryKey(ProductImg record);

    int updateByPrimaryKey(ProductImg record);

    int updateByPrimaryKeySelective(ProductImg record);

    int insert(ProductImg record);

    int insertSelective(ProductImg record);

    int insertByBatch(List<ProductImg> list);

    List<ProductImg> select(Page<ProductImg> record);

    long count(Page<ProductImg> record);

}
