package com.upedge.pms.modules.product.service;

import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerPrivateProductService{


    int countByProductId(Long productId);

    CustomerPrivateProduct selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKeySelective(CustomerPrivateProduct record);

    int insert(CustomerPrivateProduct record);

    int insertSelective(CustomerPrivateProduct record);

    List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record);

    long count(Page<CustomerPrivateProduct> record);
}

