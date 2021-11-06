package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;
import com.upedge.ums.modules.account.response.AdminAccountTypeAttrListResponse;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountTypeAttrService{

    AdminAccountTypeAttr selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AdminAccountTypeAttr record);

    int updateByPrimaryKeySelective(AdminAccountTypeAttr record);

    int insert(AdminAccountTypeAttr record);

    int insertSelective(AdminAccountTypeAttr record);

    List<AdminAccountTypeAttr> select(Page<AdminAccountTypeAttr> record);

    long count(Page<AdminAccountTypeAttr> record);

    AdminAccountTypeAttrListResponse listByAccountType(Integer accountType);
}

