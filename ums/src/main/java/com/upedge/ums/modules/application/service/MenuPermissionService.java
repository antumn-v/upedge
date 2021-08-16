package com.upedge.ums.modules.application.service;

import com.upedge.ums.modules.application.entity.MenuPermission;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface MenuPermissionService{

    MenuPermission selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(MenuPermission record);

    int updateByPrimaryKeySelective(MenuPermission record);

    int insert(MenuPermission record);

    int insertSelective(MenuPermission record);

    List<MenuPermission> select(Page<MenuPermission> record);

    long count(Page<MenuPermission> record);
}

