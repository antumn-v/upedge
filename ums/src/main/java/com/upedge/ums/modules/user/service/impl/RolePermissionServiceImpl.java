package com.upedge.ums.modules.user.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.RolePermissionDao;
import com.upedge.ums.modules.user.entity.RolePermission;
import com.upedge.ums.modules.user.service.RolePermissionService;


@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long roleId) {
        RolePermission record = new RolePermission();
        record.setRoleId(roleId);
        return rolePermissionDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(RolePermission record) {
        return rolePermissionDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(RolePermission record) {
        return rolePermissionDao.insert(record);
    }

    @Override
    public List<String> selectPermissionByRole(Long roleId) {
        return rolePermissionDao.selectPermissionByRole(roleId);
    }

    /**
     *
     */
    public RolePermission selectByPrimaryKey(Long roleId){
        RolePermission record = new RolePermission();
        record.setRoleId(roleId);
        return rolePermissionDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(RolePermission record) {
        return rolePermissionDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(RolePermission record) {
        return rolePermissionDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<RolePermission> select(Page<RolePermission> record){
        record.initFromNum();
        return rolePermissionDao.select(record);
    }

    /**
    *
    */
    public long count(Page<RolePermission> record){
        return rolePermissionDao.count(record);
    }

}