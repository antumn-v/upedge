package com.upedge.ums.modules.user.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.vo.CustomerDetailVo;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerDao{

    Long countCustomerDetail(Page<CustomerDetailVo> customerDetailVoPage);

    List<CustomerDetailVo> selectCustomerDetail(Page<CustomerDetailVo> customerDetailVoPage);

    Customer selectByPrimaryKey(Customer record);

    int deleteByPrimaryKey(Customer record);

    int updateByPrimaryKey(Customer record);

    int updateByPrimaryKeySelective(Customer record);

    int insert(Customer record);

    int insertSelective(Customer record);

    int insertByBatch(List<Customer> list);

    List<Customer> select(Page<Customer> record);

    long count(Page<Customer> record);

}
