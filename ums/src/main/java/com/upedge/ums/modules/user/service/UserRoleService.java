package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.UserRole;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface UserRoleService{

    UserRole selectByPrimaryKey(Long userId);

    int deleteByPrimaryKey(Long userId);

    int updateByPrimaryKey(UserRole record);

    int updateByPrimaryKeySelective(UserRole record);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> select(Page<UserRole> record);

    long count(Page<UserRole> record);
}

