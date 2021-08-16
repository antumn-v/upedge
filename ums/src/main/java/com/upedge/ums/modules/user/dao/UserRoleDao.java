package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface UserRoleDao{

    UserRole selectByPrimaryKey(UserRole record);

    int deleteByPrimaryKey(UserRole record);

    int updateByPrimaryKey(UserRole record);

    int updateByPrimaryKeySelective(UserRole record);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    int insertByBatch(List<UserRole> list);

    List<UserRole> select(Page<UserRole> record);

    long count(Page<UserRole> record);

}
