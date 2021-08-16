package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface RoleDao{

    Role selectRoleByUser(Long userId);

    Role selectByApplicationIdAndRoleCode(@Param("applicationId") Long applicationId,
                                          @Param("roleCode")String roleCode);

    Role selectByPrimaryKey(Role record);

    int deleteByPrimaryKey(Role record);

    int updateByPrimaryKey(Role record);

    int updateByPrimaryKeySelective(Role record);

    int insert(Role record);

    int insertSelective(Role record);

    int insertByBatch(List<Role> list);

    List<Role> select(Page<Role> record);

    long count(Page<Role> record);

}
