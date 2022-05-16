package com.upedge.ums.modules.manager.service;

import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerManagerService{

    CustomerManager selectByPrimaryKey(Long customerId);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerManager record);

    int updateByPrimaryKeySelective(CustomerManager record);

    int insert(CustomerManager record);

    int insertSelective(CustomerManager record);

    List<CustomerManager> select(Page<CustomerManager> record);

    long count(Page<CustomerManager> record);
}

