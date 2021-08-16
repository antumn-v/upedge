package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CustomerDao{

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
