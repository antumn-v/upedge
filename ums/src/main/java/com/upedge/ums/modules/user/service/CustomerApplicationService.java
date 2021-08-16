package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.CustomerApplication;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerApplicationService{

    CustomerApplication selectByPrimaryKey(Long customerId);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerApplication record);

    int updateByPrimaryKeySelective(CustomerApplication record);

    int insert(CustomerApplication record);

    int insertSelective(CustomerApplication record);

    List<CustomerApplication> select(Page<CustomerApplication> record);

    long count(Page<CustomerApplication> record);
}

