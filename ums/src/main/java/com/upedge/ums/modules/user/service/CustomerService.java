package com.upedge.ums.modules.user.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.vo.CustomerDetailVo;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerService{

    BaseResponse selectCustomerDetail(Page<CustomerDetailVo> customerDetailVoPage);

    Customer selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Customer record);

    int updateByPrimaryKeySelective(Customer record);

    int insert(Customer record);

    int insertSelective(Customer record);

    List<Customer> select(Page<Customer> record);

    long count(Page<Customer> record);
}

