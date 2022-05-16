package com.upedge.ums.modules.manager.dao;

import com.upedge.ums.modules.manager.entity.CustomerManager;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CustomerManagerDao{

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
