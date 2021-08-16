package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.UserInfo;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface UserInfoService{

    UserInfo selectByEmail(String email);

    UserInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(UserInfo record);

    int updateByPrimaryKeySelective(UserInfo record);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> select(Page<UserInfo> record);

    long count(Page<UserInfo> record);
}

