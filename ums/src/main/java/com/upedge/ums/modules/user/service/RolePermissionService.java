package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.RolePermission;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface RolePermissionService{

    List<String> selectPermissionByRole( Long roleId);

    RolePermission selectByPrimaryKey(Long roleId);

    int deleteByPrimaryKey(Long roleId);

    int updateByPrimaryKey(RolePermission record);

    int updateByPrimaryKeySelective(RolePermission record);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> select(Page<RolePermission> record);

    long count(Page<RolePermission> record);
}

