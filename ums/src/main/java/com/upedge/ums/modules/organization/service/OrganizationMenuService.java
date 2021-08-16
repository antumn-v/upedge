package com.upedge.ums.modules.organization.service;

import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrganizationMenuService{

    OrganizationMenu selectByPrimaryKey(Long orgId);

    int deleteByPrimaryKey(Long orgId);

    int updateByPrimaryKey(OrganizationMenu record);

    int updateByPrimaryKeySelective(OrganizationMenu record);

    int insert(OrganizationMenu record);

    int insertSelective(OrganizationMenu record);

    List<OrganizationMenu> select(Page<OrganizationMenu> record);

    long count(Page<OrganizationMenu> record);
}

