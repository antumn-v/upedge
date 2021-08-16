package com.upedge.ums.modules.account.service.impl;

import com.upedge.ums.modules.account.entity.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.AccountUserDao;
import com.upedge.ums.modules.account.entity.AccountUser;
import com.upedge.ums.modules.account.service.AccountUserService;


@Service
public class AccountUserServiceImpl implements AccountUserService {

    @Autowired
    private AccountUserDao accountUserDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long userId) {
        AccountUser record = new AccountUser();
        record.setUserId(userId);
        return accountUserDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AccountUser record) {
        return accountUserDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AccountUser record) {
        return accountUserDao.insert(record);
    }

    @Override
    public Account selectUserAccount(Long userId) {
        return accountUserDao.selectUserAccount(userId);
    }

    /**
     *
     */
    public AccountUser selectByPrimaryKey(Long userId){
        AccountUser record = new AccountUser();
        record.setUserId(userId);
        return accountUserDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AccountUser record) {
        return accountUserDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AccountUser record) {
        return accountUserDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AccountUser> select(Page<AccountUser> record){
        record.initFromNum();
        return accountUserDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AccountUser> record){
        return accountUserDao.count(record);
    }

}