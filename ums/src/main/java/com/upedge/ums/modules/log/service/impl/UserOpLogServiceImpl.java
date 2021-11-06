package com.upedge.ums.modules.log.service.impl;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.log.dao.UserOpLogDao;
import com.upedge.ums.modules.log.entity.UserOpLog;
import com.upedge.ums.modules.log.service.UserOpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserOpLogServiceImpl implements UserOpLogService {

    @Autowired
    private UserOpLogDao userOpLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        UserOpLog record = new UserOpLog();
        record.setId(id);
        return userOpLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(UserOpLog record) {
        return userOpLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(UserOpLog record) {
        return userOpLogDao.insert(record);
    }

    /**
     *
     */
    public UserOpLog selectByPrimaryKey(Long id){
        UserOpLog record = new UserOpLog();
        record.setId(id);
        return userOpLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(UserOpLog record) {
        return userOpLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(UserOpLog record) {
        return userOpLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<UserOpLog> select(Page<UserOpLog> record){
        record.initFromNum();
        return userOpLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<UserOpLog> record){
        return userOpLogDao.count(record);
    }

}