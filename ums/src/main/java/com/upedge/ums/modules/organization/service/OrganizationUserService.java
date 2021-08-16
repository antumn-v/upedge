package com.upedge.ums.modules.organization.service;

import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrganizationUserService{

    Organization selectUserParentOrganization(Long userId);

    List<Long> selectOrgIdsByUserId(Long userId);

    OrganizationUser selectByPrimaryKey(Long orgId);

    int deleteByPrimaryKey(Long orgId);

    int updateByPrimaryKey(OrganizationUser record);

    int updateByPrimaryKeySelective(OrganizationUser record);

    int insert(OrganizationUser record);

    int insertSelective(OrganizationUser record);

    List<OrganizationUser> select(Page<OrganizationUser> record);

    long count(Page<OrganizationUser> record);
}

