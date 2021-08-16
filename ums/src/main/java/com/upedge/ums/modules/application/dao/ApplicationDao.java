package com.upedge.ums.modules.application.dao;

import com.upedge.ums.modules.application.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ApplicationDao{

    Application selectByPrimaryKey(Application record);

    int deleteByPrimaryKey(Application record);

    int updateByPrimaryKey(Application record);

    int updateByPrimaryKeySelective(Application record);

    int insert(Application record);

    int insertSelective(Application record);

    int insertByBatch(List<Application> list);

    List<Application> select(Page<Application> record);

    long count(Page<Application> record);

}
