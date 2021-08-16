package com.upedge.ums.modules.organization.service;

import com.upedge.ums.modules.organization.entity.OrganizationRole;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrganizationRoleService{

    OrganizationRole selectByPrimaryKey(Long orgId);

    int deleteByPrimaryKey(Long orgId);

    int updateByPrimaryKey(OrganizationRole record);

    int updateByPrimaryKeySelective(OrganizationRole record);

    int insert(OrganizationRole record);

    int insertSelective(OrganizationRole record);

    List<OrganizationRole> select(Page<OrganizationRole> record);

    long count(Page<OrganizationRole> record);
}

