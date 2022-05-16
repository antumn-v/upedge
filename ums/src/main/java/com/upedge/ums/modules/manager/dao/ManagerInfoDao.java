package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerInfo;

import java.util.List;

/**
 * @author gx
 */
public interface ManagerInfoDao{

    ManagerInfo selectByInviteCode(String inviteCode);

    ManagerInfo selectByPrimaryKey(ManagerInfo record);

    int deleteByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKeySelective(ManagerInfo record);

    int insert(ManagerInfo record);

    int insertSelective(ManagerInfo record);

    int insertByBatch(List<ManagerInfo> list);

    List<ManagerInfo> select(Page<ManagerInfo> record);

    long count(Page<ManagerInfo> record);

}
