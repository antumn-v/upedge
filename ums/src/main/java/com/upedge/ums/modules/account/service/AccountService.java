package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.Account;
import com.upedge.common.base.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface AccountService{

    boolean addAccountBalance(Long  accountId, BigDecimal amount);

    Account selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Account record);

    int updateByPrimaryKeySelective(Account record);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> select(Page<Account> record);

    long count(Page<Account> record);
}

