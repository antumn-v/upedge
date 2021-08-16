package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AccountLogService{

    AccountLog selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(AccountLog record);

    int updateByPrimaryKeySelective(AccountLog record);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    List<AccountLog> select(Page<AccountLog> record);

    long count(Page<AccountLog> record);
}

