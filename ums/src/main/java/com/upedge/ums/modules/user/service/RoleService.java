package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.Role;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RoleService{

    Role selectRoleByUser(Long userId);

    Role selectApplicationDefaultRole(Long applicationId);

    Role selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Role record);

    int updateByPrimaryKeySelective(Role record);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> select(Page<Role> record);

    long count(Page<Role> record);
}

