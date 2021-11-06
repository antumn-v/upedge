package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountPayoneer;

import java.util.List;

/**
 * @author xwCui
 */
public interface AccountPayoneerService{

    int removeAccountPayoneer(Long accountId,
                              Integer payoneerId);

    AccountPayoneer selectByPrimaryKey(Long accountId);

    int deleteByPrimaryKey(Long accountId);

    int updateByPrimaryKey(AccountPayoneer record);

    int updateByPrimaryKeySelective(AccountPayoneer record);

    int insert(AccountPayoneer record);

    int insertSelective(AccountPayoneer record);

    List<AccountPayoneer> select(Page<AccountPayoneer> record);

    long count(Page<AccountPayoneer> record);
}

