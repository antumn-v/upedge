package com.upedge.ums.modules.user.service.impl;

import com.upedge.ums.enums.ApplicationDefaultRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.RoleDao;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Role record = new Role();
        record.setId(id);
        return roleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Role record) {
        return roleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Role record) {
        return roleDao.insert(record);
    }

    @Override
    public  Role selectRoleByUser(Long userId) {
        return roleDao.selectRoleByUser(userId);
    }

    @Override
    public Role selectApplicationDefaultRole(Long applicationId) {
        for (ApplicationDefaultRole value : ApplicationDefaultRole.values()) {
            while (value.getApplicationId().equals(applicationId)){
                Role role = roleDao.selectByApplicationIdAndRoleCode(applicationId,value.getRoleCode());
                return role;
            }
        }
        return null;

    }

    /**
     *
     */
    public Role selectByPrimaryKey(Long id){
        Role record = new Role();
        record.setId(id);
        return roleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Role record) {
        return roleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Role record) {
        return roleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Role> select(Page<Role> record){
        record.initFromNum();
        return roleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Role> record){
        return roleDao.count(record);
    }

}