package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.user.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface RoleMenuDao{

    List<Menu> selectRoleMenuByApplication(@Param("roleId") Long roleId,
                                           @Param("applicationId") Long applicationId);

    RoleMenu selectByPrimaryKey(RoleMenu record);

    int deleteByPrimaryKey(RoleMenu record);

    int updateByPrimaryKey(RoleMenu record);

    int updateByPrimaryKeySelective(RoleMenu record);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    int insertByBatch(List<RoleMenu> list);

    List<RoleMenu> select(Page<RoleMenu> record);

    long count(Page<RoleMenu> record);

}
