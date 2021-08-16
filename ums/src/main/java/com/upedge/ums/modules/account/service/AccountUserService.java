package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AccountUserService{

    Account selectUserAccount(Long userId);

    AccountUser selectByPrimaryKey(Long userId);

    int deleteByPrimaryKey(Long userId);

    int updateByPrimaryKey(AccountUser record);

    int updateByPrimaryKeySelective(AccountUser record);

    int insert(AccountUser record);

    int insertSelective(AccountUser record);

    List<AccountUser> select(Page<AccountUser> record);

    long count(Page<AccountUser> record);
}

