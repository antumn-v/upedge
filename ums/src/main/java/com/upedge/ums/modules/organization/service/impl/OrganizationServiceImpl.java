package com.upedge.ums.modules.organization.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationDao;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.service.OrganizationService;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Organization record = new Organization();
        record.setId(id);
        return organizationDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Organization record) {
        return organizationDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Organization record) {
        return organizationDao.insert(record);
    }

    /**
     *
     */
    public Organization selectByPrimaryKey(Long id){
        Organization record = new Organization();
        record.setId(id);
        return organizationDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Organization record) {
        return organizationDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Organization record) {
        return organizationDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Organization> select(Page<Organization> record){
        record.initFromNum();
        return organizationDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Organization> record){
        return organizationDao.count(record);
    }

}