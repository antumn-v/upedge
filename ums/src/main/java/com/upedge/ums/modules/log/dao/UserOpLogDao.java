package com.upedge.ums.modules.log.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.log.entity.UserOpLog;

import java.util.List;

/**
 * @author author
 */
public interface UserOpLogDao{

    UserOpLog selectByPrimaryKey(UserOpLog record);

    int deleteByPrimaryKey(UserOpLog record);

    int updateByPrimaryKey(UserOpLog record);

    int updateByPrimaryKeySelective(UserOpLog record);

    int insert(UserOpLog record);

    int insertSelective(UserOpLog record);

    int insertByBatch(List<UserOpLog> list);

    List<UserOpLog> select(Page<UserOpLog> record);

    long count(Page<UserOpLog> record);

}
