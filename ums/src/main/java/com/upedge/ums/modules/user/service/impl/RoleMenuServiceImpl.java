package com.upedge.ums.modules.user.service.impl;

import com.upedge.ums.modules.application.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.RoleMenuDao;
import com.upedge.ums.modules.user.entity.RoleMenu;
import com.upedge.ums.modules.user.service.RoleMenuService;


@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long roleId) {
        RoleMenu record = new RoleMenu();
        record.setRoleId(roleId);
        return roleMenuDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RoleMenu record) {
        return roleMenuDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RoleMenu record) {
        return roleMenuDao.insert(record);
    }

    @Override
    public List<Menu> selectRoleMenuByApplication(Long roleId, Long applicationId) {
        return roleMenuDao.selectRoleMenuByApplication(roleId, applicationId);
    }

    /**
     *
     */
    public RoleMenu selectByPrimaryKey(Long roleId){
        RoleMenu record = new RoleMenu();
        record.setRoleId(roleId);
        return roleMenuDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RoleMenu record) {
        return roleMenuDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RoleMenu record) {
        return roleMenuDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RoleMenu> select(Page<RoleMenu> record){
        record.initFromNum();
        return roleMenuDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RoleMenu> record){
        return roleMenuDao.count(record);
    }

}