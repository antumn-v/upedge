package com.upedge.pms.modules.product.dao;

import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CustomerPrivateProductDao{

    CustomerPrivateProduct selectByPrimaryKey(CustomerPrivateProduct record);

    int deleteByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKey(CustomerPrivateProduct record);

    int updateByPrimaryKeySelective(CustomerPrivateProduct record);

    int insert(CustomerPrivateProduct record);

    int insertSelective(CustomerPrivateProduct record);

    int insertByBatch(List<CustomerPrivateProduct> list);

    List<CustomerPrivateProduct> select(Page<CustomerPrivateProduct> record);

    long count(Page<CustomerPrivateProduct> record);

}
