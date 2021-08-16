package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.CustomerApplication;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CustomerApplicationDao{

    CustomerApplication selectByPrimaryKey(CustomerApplication record);

    int deleteByPrimaryKey(CustomerApplication record);

    int updateByPrimaryKey(CustomerApplication record);

    int updateByPrimaryKeySelective(CustomerApplication record);

    int insert(CustomerApplication record);

    int insertSelective(CustomerApplication record);

    int insertByBatch(List<CustomerApplication> list);

    List<CustomerApplication> select(Page<CustomerApplication> record);

    long count(Page<CustomerApplication> record);

}
