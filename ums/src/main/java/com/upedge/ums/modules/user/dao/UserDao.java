package com.upedge.ums.modules.user.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface UserDao{


    User selectByLoginName(String loginName);

    void refreshLoginData(User user);

    int resetUserPassword(User user);

    User selectUserByLoginName(@Param("loginName") String loginName);

    User selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(User record);

    int updateByPrimaryKey(User record);

    int updateByPrimaryKeySelective(User record);

    int insert(User record);

    int insertSelective(User record);

    int insertByBatch(List<User> list);

    List<User> select(Page<User> record);

    long count(Page<User> record);

}
