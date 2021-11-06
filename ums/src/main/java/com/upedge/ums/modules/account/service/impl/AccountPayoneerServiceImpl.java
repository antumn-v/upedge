package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.dao.AccountPayoneerDao;
import com.upedge.ums.modules.account.entity.AccountPayoneer;
import com.upedge.ums.modules.account.service.AccountPayoneerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountPayoneerServiceImpl implements AccountPayoneerService {

    @Autowired
    private AccountPayoneerDao accountPayoneerDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long accountId) {
        AccountPayoneer record = new AccountPayoneer();
        record.setAccountId(accountId);
        return accountPayoneerDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AccountPayoneer record) {
        return accountPayoneerDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AccountPayoneer record) {
        return accountPayoneerDao.insert(record);
    }

    @Override
    public int removeAccountPayoneer(Long accountId, Integer payoneerId) {
        return accountPayoneerDao.removeAccountPayoneer(accountId, payoneerId);
    }

    /**
     *
     */
    public AccountPayoneer selectByPrimaryKey(Long accountId){
        AccountPayoneer record = new AccountPayoneer();
        record.setAccountId(accountId);
        return accountPayoneerDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AccountPayoneer record) {
        return accountPayoneerDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AccountPayoneer record) {
        return accountPayoneerDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AccountPayoneer> select(Page<AccountPayoneer> record){
        record.initFromNum();
        return accountPayoneerDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AccountPayoneer> record){
        return accountPayoneerDao.count(record);
    }

}