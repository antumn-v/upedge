package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.CustomerIoss;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerIossService{

    CustomerIoss selectByCustomerId(Long customerId);

    CustomerIoss selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerIoss record);

    int updateByPrimaryKeySelective(CustomerIoss record);

    int insert(CustomerIoss record);

    int insertSelective(CustomerIoss record);

    List<CustomerIoss> select(Page<CustomerIoss> record);

    long count(Page<CustomerIoss> record);
}

