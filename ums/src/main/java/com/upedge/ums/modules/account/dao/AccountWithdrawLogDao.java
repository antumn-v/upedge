package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;

import java.util.List;

/**
 * @author gx
 */
public interface AccountWithdrawLogDao{

    List<AccountWithdrawLog> selectAccountPendingWithdrawRequest(Long accountId);

    AccountWithdrawLog selectByPrimaryKey(AccountWithdrawLog record);

    int deleteByPrimaryKey(AccountWithdrawLog record);

    int updateByPrimaryKey(AccountWithdrawLog record);

    int updateByPrimaryKeySelective(AccountWithdrawLog record);

    int insert(AccountWithdrawLog record);

    int insertSelective(AccountWithdrawLog record);

    int insertByBatch(List<AccountWithdrawLog> list);

    List<AccountWithdrawLog> select(Page<AccountWithdrawLog> record);

    long count(Page<AccountWithdrawLog> record);

}
