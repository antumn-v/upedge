package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.UserRoleDao;
import com.upedge.ums.modules.user.entity.UserRole;
import com.upedge.ums.modules.user.service.UserRoleService;


@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long userId) {
        UserRole record = new UserRole();
        record.setUserId(userId);
        return userRoleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(UserRole record) {
        return userRoleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(UserRole record) {
        return userRoleDao.insert(record);
    }

    /**
     *
     */
    public UserRole selectByPrimaryKey(Long userId){
        UserRole record = new UserRole();
        record.setUserId(userId);
        return userRoleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(UserRole record) {
        return userRoleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(UserRole record) {
        return userRoleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<UserRole> select(Page<UserRole> record){
        record.initFromNum();
        return userRoleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<UserRole> record){
        return userRoleDao.count(record);
    }

}