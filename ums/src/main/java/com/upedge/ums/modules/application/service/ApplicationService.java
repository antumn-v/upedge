package com.upedge.ums.modules.application.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.entity.Application;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ApplicationService{

    List<Application> allApplications();

    Application selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Application record);

    int updateByPrimaryKeySelective(Application record);

    int insert(Application record);

    int insertSelective(Application record);

    List<Application> select(Page<Application> record);

    long count(Page<Application> record);
}

