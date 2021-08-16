package com.upedge.ums.modules.address.service;

import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerAddressService{

    CustomerAddress selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerAddress record);

    int updateByPrimaryKeySelective(CustomerAddress record);

    int insert(CustomerAddress record);

    int insertSelective(CustomerAddress record);

    List<CustomerAddress> select(Page<CustomerAddress> record);

    long count(Page<CustomerAddress> record);
}

