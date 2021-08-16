package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.entity.UserInfo;
import com.upedge.ums.modules.user.service.UserInfoService;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        UserInfo record = new UserInfo();
        record.setId(id);
        return userInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(UserInfo record) {
        return userInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(UserInfo record) {
        return userInfoDao.insert(record);
    }

    @Override
    public UserInfo selectByEmail(String email) {
        return userInfoDao.selectByEmail(email);
    }

    /**
     *
     */
    public UserInfo selectByPrimaryKey(Long id){
        UserInfo record = new UserInfo();
        record.setId(id);
        return userInfoDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(UserInfo record) {
        return userInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(UserInfo record) {
        return userInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<UserInfo> select(Page<UserInfo> record){
        record.initFromNum();
        return userInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<UserInfo> record){
        return userInfoDao.count(record);
    }

}