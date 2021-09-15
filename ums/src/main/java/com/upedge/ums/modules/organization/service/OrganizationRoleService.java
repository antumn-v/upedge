package com.upedge.ums.modules.organization.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.RoleVo;
import com.upedge.ums.modules.organization.entity.OrganizationRole;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.organization.request.OrganizationRoleDeleteRequest;
import com.upedge.ums.modules.organization.vo.OrganizationRoleVo;

import java.util.List;

/**
 * @author gx
 */
public interface OrganizationRoleService{

    List<OrganizationRoleVo> organizationRoles(Long orgId);

    OrganizationRole selectByPrimaryKey(Long orgId);

    BaseResponse deleteOrganizationRole(OrganizationRoleDeleteRequest request);

    int deleteByPrimaryKey(Long orgId);

    int updateByPrimaryKey(OrganizationRole record);

    int updateByPrimaryKeySelective(OrganizationRole record);

    int insert(OrganizationRole record);

    int insertSelective(OrganizationRole record);

    List<OrganizationRole> select(Page<OrganizationRole> record);

    long count(Page<OrganizationRole> record);
}

