package com.upedge.ums.modules.organization.dao;

import com.upedge.ums.modules.organization.entity.OrganizationRole;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrganizationRoleDao{

    OrganizationRole selectByPrimaryKey(OrganizationRole record);

    int deleteByPrimaryKey(OrganizationRole record);

    int updateByPrimaryKey(OrganizationRole record);

    int updateByPrimaryKeySelective(OrganizationRole record);

    int insert(OrganizationRole record);

    int insertSelective(OrganizationRole record);

    int insertByBatch(List<OrganizationRole> list);

    List<OrganizationRole> select(Page<OrganizationRole> record);

    long count(Page<OrganizationRole> record);

}
