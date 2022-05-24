package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.CustomerManager;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerManagerDao{

    List<CustomerManager> selectAll();

    CustomerManager selectByPrimaryKey(CustomerManager record);

    int deleteByPrimaryKey(CustomerManager record);

    int updateByPrimaryKey(CustomerManager record);

    int updateByPrimaryKeySelective(CustomerManager record);

    int insert(CustomerManager record);

    int insertSelective(CustomerManager record);

    int insertByBatch(List<CustomerManager> list);

    List<CustomerManager> select(Page<CustomerManager> record);

    long count(Page<CustomerManager> record);

}
