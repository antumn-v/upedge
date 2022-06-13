package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;
import com.upedge.ums.modules.account.request.AccountBalanceWithdrawRequest;

import java.util.List;

/**
 * @author gx
 */
public interface AccountWithdrawLogService{

    BaseResponse accountWithdrawRequest(AccountBalanceWithdrawRequest request, Session session);

    BaseResponse withdrawConfirm(Long id, Session session);

    BaseResponse reject(Long id,Session session);

    AccountWithdrawLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AccountWithdrawLog record);

    int updateByPrimaryKeySelective(AccountWithdrawLog record);

    int insert(AccountWithdrawLog record);

    int insertSelective(AccountWithdrawLog record);

    List<AccountWithdrawLog> select(Page<AccountWithdrawLog> record);

    long count(Page<AccountWithdrawLog> record);
}

