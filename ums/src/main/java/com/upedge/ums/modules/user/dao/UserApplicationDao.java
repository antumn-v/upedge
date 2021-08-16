package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.UserApplication;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface UserApplicationDao{

    List<Long> selectApplicationIdsByUser(Long userId);

    UserApplication selectByPrimaryKey(UserApplication record);

    int deleteByPrimaryKey(UserApplication record);

    int updateByPrimaryKey(UserApplication record);

    int updateByPrimaryKeySelective(UserApplication record);

    int insert(UserApplication record);

    int insertSelective(UserApplication record);

    int insertByBatch(List<UserApplication> list);

    List<UserApplication> select(Page<UserApplication> record);

    long count(Page<UserApplication> record);

}
