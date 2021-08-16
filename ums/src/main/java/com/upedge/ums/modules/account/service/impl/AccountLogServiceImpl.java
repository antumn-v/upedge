package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.AccountLogDao;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.service.AccountLogService;


@Service
public class AccountLogServiceImpl implements AccountLogService {

    @Autowired
    private AccountLogDao accountLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        AccountLog record = new AccountLog();
        record.setId(id);
        return accountLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AccountLog record) {
        return accountLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AccountLog record) {
        return accountLogDao.insert(record);
    }

    /**
     *
     */
    public AccountLog selectByPrimaryKey(Integer id){
        AccountLog record = new AccountLog();
        record.setId(id);
        return accountLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AccountLog record) {
        return accountLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AccountLog record) {
        return accountLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AccountLog> select(Page<AccountLog> record){
        record.initFromNum();
        return accountLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AccountLog> record){
        return accountLogDao.count(record);
    }

}