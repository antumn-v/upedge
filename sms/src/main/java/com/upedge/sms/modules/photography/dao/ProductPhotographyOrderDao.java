package com.upedge.sms.modules.photography.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrder;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author gx
 */
public interface ProductPhotographyOrderDao {

    ProductPhotographyOrder selectByPrimaryKey(ProductPhotographyOrder record);

    int updateOrderAsPaid(@Param("id") Long id, @Param("paymentId") Long paymentId, @Param("payTime") Date payTime);

    int deleteByPrimaryKey(ProductPhotographyOrder record);

    int updateByPrimaryKey(ProductPhotographyOrder record);

    int updateByPrimaryKeySelective(ProductPhotographyOrder record);

    int insert(ProductPhotographyOrder record);

    int insertSelective(ProductPhotographyOrder record);

    int insertByBatch(List<ProductPhotographyOrder> list);

    List<ProductPhotographyOrder> select(Page<ProductPhotographyOrder> record);

    long count(Page<ProductPhotographyOrder> record);

}
