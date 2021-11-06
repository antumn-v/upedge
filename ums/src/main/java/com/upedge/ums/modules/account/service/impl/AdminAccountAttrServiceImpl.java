package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.dao.AdminAccountAttrDao;
import com.upedge.ums.modules.account.entity.AdminAccountAttr;
import com.upedge.ums.modules.account.service.AdminAccountAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminAccountAttrServiceImpl implements AdminAccountAttrService {

    @Autowired
    private AdminAccountAttrDao adminAccountAttrDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long accountId) {
        AdminAccountAttr record = new AdminAccountAttr();
        record.setAccountId(accountId);
        return adminAccountAttrDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(AdminAccountAttr record) {
        return adminAccountAttrDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(AdminAccountAttr record) {
        return adminAccountAttrDao.insert(record);
    }

    /**
     *
     */
    public AdminAccountAttr selectByPrimaryKey(Long accountId){
        AdminAccountAttr record = new AdminAccountAttr();
        record.setAccountId(accountId);
        return adminAccountAttrDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(AdminAccountAttr record) {
        return adminAccountAttrDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(AdminAccountAttr record) {
        return adminAccountAttrDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<AdminAccountAttr> select(Page<AdminAccountAttr> record){
        record.initFromNum();
        return adminAccountAttrDao.select(record);
    }

    /**
    *
    */
    public long count(Page<AdminAccountAttr> record){
        return adminAccountAttrDao.count(record);
    }

}