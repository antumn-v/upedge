package com.upedge.ums.modules.organization.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationMenuDao;
import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import com.upedge.ums.modules.organization.service.OrganizationMenuService;


@Service
public class OrganizationMenuServiceImpl implements OrganizationMenuService {

    @Autowired
    private OrganizationMenuDao organizationMenuDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orgId) {
        OrganizationMenu record = new OrganizationMenu();
        record.setOrgId(orgId);
        return organizationMenuDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrganizationMenu record) {
        return organizationMenuDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrganizationMenu record) {
        return organizationMenuDao.insert(record);
    }

    /**
     *
     */
    public OrganizationMenu selectByPrimaryKey(Long orgId){
        OrganizationMenu record = new OrganizationMenu();
        record.setOrgId(orgId);
        return organizationMenuDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrganizationMenu record) {
        return organizationMenuDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrganizationMenu record) {
        return organizationMenuDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrganizationMenu> select(Page<OrganizationMenu> record){
        record.initFromNum();
        return organizationMenuDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrganizationMenu> record){
        return organizationMenuDao.count(record);
    }

}