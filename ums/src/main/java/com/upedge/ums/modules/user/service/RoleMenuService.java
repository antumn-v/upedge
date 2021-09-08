package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.user.entity.RoleMenu;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface RoleMenuService{

    List<Menu> selectRoleMenuByApplication( Long roleId,
                                           Long applicationId);


    RoleMenu selectByPrimaryKey(Long roleId);

    int deleteByPrimaryKey(Long roleId);

    int updateByPrimaryKey(RoleMenu record);

    int updateByPrimaryKeySelective(RoleMenu record);

    int insert(RoleMenu record);

    int batchInsert(List<RoleMenu> roleMenus);

    int insertSelective(RoleMenu record);

    List<RoleMenu> select(Page<RoleMenu> record);

    long count(Page<RoleMenu> record);
}

