package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface RolePermissionDao{

    List<String> selectPermissionByRole(Long roleId);

    RolePermission selectByPrimaryKey(RolePermission record);

    int deleteByPrimaryKey(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    int updateByPrimaryKeySelective(RolePermission record);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    int insertByBatch(List<RolePermission> list);

    List<RolePermission> select(Page<RolePermission> record);

    long count(Page<RolePermission> record);

}
