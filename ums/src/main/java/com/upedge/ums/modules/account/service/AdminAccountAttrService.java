package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccountAttr;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountAttrService{

    AdminAccountAttr selectByPrimaryKey(Long accountId);

    int deleteByPrimaryKey(Long accountId);

    int updateByPrimaryKey(AdminAccountAttr record);

    int updateByPrimaryKeySelective(AdminAccountAttr record);

    int insert(AdminAccountAttr record);

    int insertSelective(AdminAccountAttr record);

    List<AdminAccountAttr> select(Page<AdminAccountAttr> record);

    long count(Page<AdminAccountAttr> record);
}

