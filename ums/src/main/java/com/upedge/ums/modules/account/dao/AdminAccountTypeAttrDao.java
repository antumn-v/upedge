package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminAccountTypeAttr;

import java.util.List;

/**
 * @author author
 */
public interface AdminAccountTypeAttrDao{

    AdminAccountTypeAttr selectByPrimaryKey(AdminAccountTypeAttr record);

    int deleteByPrimaryKey(AdminAccountTypeAttr record);

    int updateByPrimaryKey(AdminAccountTypeAttr record);

    int updateByPrimaryKeySelective(AdminAccountTypeAttr record);

    int insert(AdminAccountTypeAttr record);

    int insertSelective(AdminAccountTypeAttr record);

    int insertByBatch(List<AdminAccountTypeAttr> list);

    List<AdminAccountTypeAttr> select(Page<AdminAccountTypeAttr> record);

    long count(Page<AdminAccountTypeAttr> record);

    List<AdminAccountTypeAttr> listByAccountType(Integer accountType);
}
