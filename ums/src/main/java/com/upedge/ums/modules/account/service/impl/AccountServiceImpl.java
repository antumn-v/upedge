package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.AccountDao;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Account record = new Account();
        record.setId(id);
        return accountDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Account record) {
        return accountDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Account record) {
        return accountDao.insert(record);
    }

    /**
     *
     */
    public Account selectByPrimaryKey(Long id){
        Account record = new Account();
        record.setId(id);
        return accountDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Account record) {
        return accountDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Account record) {
        return accountDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Account> select(Page<Account> record){
        record.initFromNum();
        return accountDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Account> record){
        return accountDao.count(record);
    }

}