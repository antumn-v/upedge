package com.upedge.pms.modules.product.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.common.base.Page;
import com.upedge.thirdparty.ali1688.vo.ProductVo;

import java.util.List;

/**
 * @author gx
 */
public interface ProductService{

    BaseResponse importFrom1688(ProductVo productVo, Session session);

    Product selectByProductSku(String productSku);

    Product selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Product record);

    int updateByPrimaryKeySelective(Product record);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> select(Page<Product> record);

    long count(Page<Product> record);
}

