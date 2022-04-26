package com.upedge.sms.modules.photography.service;

import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ProductPhotographyOrderItemService{

    List<ProductPhotographyOrderItem> selectByOrderId(Long orderId);

    ProductPhotographyOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ProductPhotographyOrderItem record);

    int updateByPrimaryKeySelective(ProductPhotographyOrderItem record);

    int insert(ProductPhotographyOrderItem record);

    int insertByBatch(List<ProductPhotographyOrderItem> productPhotographyOrderItems);

    int insertSelective(ProductPhotographyOrderItem record);

    List<ProductPhotographyOrderItem> select(Page<ProductPhotographyOrderItem> record);

    long count(Page<ProductPhotographyOrderItem> record);
}

