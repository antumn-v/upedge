package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.UserApplication;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface UserApplicationService{

    List<Long> selectApplicationIdsByUser(Long userId);

    UserApplication selectByPrimaryKey(Long userId);

    int deleteByPrimaryKey(Long userId);

    int updateByPrimaryKey(UserApplication record);

    int updateByPrimaryKeySelective(UserApplication record);

    int insert(UserApplication record);

    int insertSelective(UserApplication record);

    List<UserApplication> select(Page<UserApplication> record);

    long count(Page<UserApplication> record);
}

