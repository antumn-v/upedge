package com.upedge.ums.modules.organization.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationRoleDao;
import com.upedge.ums.modules.organization.entity.OrganizationRole;
import com.upedge.ums.modules.organization.service.OrganizationRoleService;


@Service
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    @Autowired
    private OrganizationRoleDao organizationRoleDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orgId) {
        OrganizationRole record = new OrganizationRole();
        record.setOrgId(orgId);
        return organizationRoleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrganizationRole record) {
        return organizationRoleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrganizationRole record) {
        return organizationRoleDao.insert(record);
    }

    /**
     *
     */
    public OrganizationRole selectByPrimaryKey(Long orgId){
        OrganizationRole record = new OrganizationRole();
        record.setOrgId(orgId);
        return organizationRoleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrganizationRole record) {
        return organizationRoleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrganizationRole record) {
        return organizationRoleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrganizationRole> select(Page<OrganizationRole> record){
        record.initFromNum();
        return organizationRoleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrganizationRole> record){
        return organizationRoleDao.count(record);
    }

}