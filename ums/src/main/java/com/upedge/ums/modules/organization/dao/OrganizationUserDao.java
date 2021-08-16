package com.upedge.ums.modules.organization.dao;

import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrganizationUserDao{

    List<Long> selectOrgIdsByUserId(Long userId);

    Organization selectUserParentOrganization(Long userId);

    OrganizationUser selectByPrimaryKey(OrganizationUser record);

    int deleteByPrimaryKey(OrganizationUser record);

    int updateByPrimaryKey(OrganizationUser record);

    int updateByPrimaryKeySelective(OrganizationUser record);

    int insert(OrganizationUser record);

    int insertSelective(OrganizationUser record);

    int insertByBatch(List<OrganizationUser> list);

    List<OrganizationUser> select(Page<OrganizationUser> record);

    long count(Page<OrganizationUser> record);

}
