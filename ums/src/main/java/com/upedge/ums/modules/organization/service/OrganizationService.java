package com.upedge.ums.modules.organization.service;

import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface OrganizationService{

    Organization selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Organization record);

    int updateByPrimaryKeySelective(Organization record);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> select(Page<Organization> record);

    long count(Page<Organization> record);
}

