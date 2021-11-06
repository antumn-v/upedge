package com.upedge.ums.modules.log.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.log.entity.UserOpLog;

import java.util.List;

/**
 * @author author
 */
public interface UserOpLogService{

    UserOpLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(UserOpLog record);

    int updateByPrimaryKeySelective(UserOpLog record);

    int insert(UserOpLog record);

    int insertSelective(UserOpLog record);

    List<UserOpLog> select(Page<UserOpLog> record);

    long count(Page<UserOpLog> record);
}

