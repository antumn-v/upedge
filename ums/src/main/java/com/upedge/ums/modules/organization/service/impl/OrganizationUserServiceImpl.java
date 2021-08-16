package com.upedge.ums.modules.organization.service.impl;

import com.upedge.ums.modules.organization.entity.Organization;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationUserDao;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import com.upedge.ums.modules.organization.service.OrganizationUserService;


@Service
public class OrganizationUserServiceImpl implements OrganizationUserService {

    @Autowired
    private OrganizationUserDao organizationUserDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orgId) {
        OrganizationUser record = new OrganizationUser();
        record.setOrgId(orgId);
        return organizationUserDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrganizationUser record) {
        return organizationUserDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrganizationUser record) {
        return organizationUserDao.insert(record);
    }

    @Override
    public Organization selectUserParentOrganization(Long userId) {
        return organizationUserDao.selectUserParentOrganization(userId);
    }

    @Override
    public List<Long> selectOrgIdsByUserId(Long userId) {
        return organizationUserDao.selectOrgIdsByUserId(userId);
    }

    /**
     *
     */
    public OrganizationUser selectByPrimaryKey(Long orgId){
        OrganizationUser record = new OrganizationUser();
        record.setOrgId(orgId);
        return organizationUserDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrganizationUser record) {
        return organizationUserDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrganizationUser record) {
        return organizationUserDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrganizationUser> select(Page<OrganizationUser> record){
        record.initFromNum();
        return organizationUserDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrganizationUser> record){
        return organizationUserDao.count(record);
    }

}