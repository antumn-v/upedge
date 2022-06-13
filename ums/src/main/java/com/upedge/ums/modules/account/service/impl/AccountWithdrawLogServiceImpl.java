package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.*;
import com.upedge.ums.modules.account.dao.AccountWithdrawLogDao;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;
import com.upedge.ums.modules.account.request.AccountBalanceWithdrawRequest;
import com.upedge.ums.modules.account.service.AccountLogService;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.account.service.AccountWithdrawLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class AccountWithdrawLogServiceImpl implements AccountWithdrawLogService {

    @Autowired
    private AccountWithdrawLogDao accountWithdrawLogDao;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountLogService accountLogService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        AccountWithdrawLog record = new AccountWithdrawLog();
        record.setId(id);
        return accountWithdrawLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AccountWithdrawLog record) {
        return accountWithdrawLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AccountWithdrawLog record) {
        return accountWithdrawLogDao.insert(record);
    }

    @Override
    public BaseResponse accountWithdrawRequest(AccountBalanceWithdrawRequest request, Session session) {
        Account account = accountService.selectById(session.getAccountId());
        if (account.getBalance().compareTo(request.getBalance()) < 0
                || account.getAffiliateRebate().compareTo(request.getAffiliateRebate()) < 0
                || account.getVipRebate().compareTo(request.getVipRebate()) < 0){
            return BaseResponse.failed("Insufficient balance");
        }

        AccountWithdrawLog accountWithdrawLog = request.toWithdrawLog(session);

        insert(accountWithdrawLog);
        return BaseResponse.success();

    }

    @Transactional
    @Override
    public BaseResponse withdrawConfirm(Long id, Session session) {

        if (session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            return BaseResponse.failed("权限不足");
        }

        AccountWithdrawLog accountWithdrawLog = selectByPrimaryKey(id);

        if (null == accountWithdrawLog
        || 0 != accountWithdrawLog.getStatus()){
            return BaseResponse.failed();
        }

        Long accountId = accountWithdrawLog.getAccountId();

        String key = RedisKey.KEY_USER_ACCOUNT_PAY + accountId;

        boolean b = RedisUtil.lock(redisTemplate,key,3L,10*1000L);
        if(!b){
            return BaseResponse.failed("账户正在交易中");
        }
        int i = accountService.accountReduceBalance(accountWithdrawLog.getAccountId(),accountWithdrawLog.getBalance(),accountWithdrawLog.getAffiliateRebate(),accountWithdrawLog.getVipRebate());
        if (i == 0){
            return BaseResponse.failed("提现失败");
        }
        AccountLog accountLog = new AccountLog(accountWithdrawLog.getAccountId(),accountWithdrawLog.getCustomerId(), TransactionConstant.TransactionType.PAY_CUT_PAYMENT.getCode(),
                TransactionConstant.OrderType.BALANCE_WITHDRAW.getCode(),
                TransactionConstant.PayMethod.ACCOUNT.getCode(),
                IdGenerate.nextId(),
                accountWithdrawLog.getBalance(),
                accountWithdrawLog.getAffiliateRebate(),
                accountWithdrawLog.getVipRebate(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                new Date());
        accountLogService.insert(accountLog);


        updateByPrimaryKeySelective(accountWithdrawLog);
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse reject(Long id, Session session) {
        if (session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            return BaseResponse.failed("权限不足");
        }
        AccountWithdrawLog accountWithdrawLog = new AccountWithdrawLog();
        accountWithdrawLog.setStatus(2);
        accountWithdrawLog.setId(id);
        accountWithdrawLog.setUpdateTime(new Date());
        updateByPrimaryKeySelective(accountWithdrawLog);
        return BaseResponse.success();
    }

    /**
     *
     */
    public AccountWithdrawLog selectByPrimaryKey(Long id){
        AccountWithdrawLog record = new AccountWithdrawLog();
        record.setId(id);
        return accountWithdrawLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AccountWithdrawLog record) {
        return accountWithdrawLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AccountWithdrawLog record) {
        return accountWithdrawLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AccountWithdrawLog> select(Page<AccountWithdrawLog> record){
        record.initFromNum();
        return accountWithdrawLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AccountWithdrawLog> record){
        return accountWithdrawLogDao.count(record);
    }

}