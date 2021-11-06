package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AdminCredit;

import java.util.List;

/**
 * @author author
 */
public interface AdminCreditDao{

    AdminCredit selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(AdminCredit record);

    int updateByPrimaryKey(AdminCredit record);

    int updateByPrimaryKeySelective(AdminCredit record);

    int insert(AdminCredit record);

    int insertSelective(AdminCredit record);

    int insertByBatch(List<AdminCredit> list);

    List<AdminCredit> select(Page<AdminCredit> record);

    long count(Page<AdminCredit> record);

    AdminCredit queryAdminCreditByCustomerId(Long customerId);

    int rejectApply(AdminCredit adminCredit);

    int confirmApply(AdminCredit adminCredit);
}
