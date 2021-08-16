package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.UserApplicationDao;
import com.upedge.ums.modules.user.entity.UserApplication;
import com.upedge.ums.modules.user.service.UserApplicationService;


@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    private UserApplicationDao userApplicationDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long userId) {
        UserApplication record = new UserApplication();
        record.setUserId(userId);
        return userApplicationDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(UserApplication record) {
        return userApplicationDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(UserApplication record) {
        return userApplicationDao.insert(record);
    }

    @Override
    public List<Long> selectApplicationIdsByUser(Long userId) {
        return  userApplicationDao.selectApplicationIdsByUser(userId);
    }

    /**
     *
     */
    public UserApplication selectByPrimaryKey(Long userId){
        UserApplication record = new UserApplication();
        record.setUserId(userId);
        return userApplicationDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(UserApplication record) {
        return userApplicationDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(UserApplication record) {
        return userApplicationDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<UserApplication> select(Page<UserApplication> record){
        record.initFromNum();
        return userApplicationDao.select(record);
    }

    /**
    *
    */
    public long count(Page<UserApplication> record){
        return userApplicationDao.count(record);
    }

}