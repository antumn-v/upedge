package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface UserInfoDao{

    UserInfo selectByEmail(String email);

    UserInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    int updateByPrimaryKeySelective(UserInfo record);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    int insertByBatch(List<UserInfo> list);

    List<UserInfo> select(Page<UserInfo> record);

    long count(Page<UserInfo> record);

}
