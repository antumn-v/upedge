package com.upedge.sms.modules.photography.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;

import java.util.List;

/**
 * @author gx
 */
public interface ProductPhotographyOrderItemDao{

    List<ProductPhotographyOrderItem> selectByOrderId(Long orderId);

    ProductPhotographyOrderItem selectByPrimaryKey(ProductPhotographyOrderItem record);

    int deleteByPrimaryKey(ProductPhotographyOrderItem record);

    int updateByPrimaryKey(ProductPhotographyOrderItem record);

    int updateByPrimaryKeySelective(ProductPhotographyOrderItem record);

    int insert(ProductPhotographyOrderItem record);

    int insertSelective(ProductPhotographyOrderItem record);

    int insertByBatch(List<ProductPhotographyOrderItem> list);

    List<ProductPhotographyOrderItem> select(Page<ProductPhotographyOrderItem> record);

    long count(Page<ProductPhotographyOrderItem> record);

}
