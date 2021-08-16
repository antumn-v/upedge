package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerService{

    Customer selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Customer record);

    int updateByPrimaryKeySelective(Customer record);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> select(Page<Customer> record);

    long count(Page<Customer> record);
}

