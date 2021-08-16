package com.upedge.ums.modules.application.dao;

import com.upedge.ums.modules.application.entity.MenuPermission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface MenuPermissionDao{

    MenuPermission selectByPrimaryKey(MenuPermission record);

    int deleteByPrimaryKey(MenuPermission record);

    int updateByPrimaryKey(MenuPermission record);

    int updateByPrimaryKeySelective(MenuPermission record);

    int insert(MenuPermission record);

    int insertSelective(MenuPermission record);

    int insertByBatch(List<MenuPermission> list);

    List<MenuPermission> select(Page<MenuPermission> record);

    long count(Page<MenuPermission> record);

}
