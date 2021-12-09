package com.upedge.ums.modules.user.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerIoss;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerIossDao{

    CustomerIoss selectByCustomerId(Long customerId);

    CustomerIoss selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerIoss record);

    int updateByPrimaryKeySelective(CustomerIoss record);

    int insert(CustomerIoss record);

    int insertSelective(CustomerIoss record);

    int insertByBatch(List<CustomerIoss> list);

    List<CustomerIoss> select(Page<CustomerIoss> record);

    long count(Page<CustomerIoss> record);

}
