package com.upedge.ums.modules.organization.dao;

import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrganizationMenuDao{

    OrganizationMenu selectByPrimaryKey(OrganizationMenu record);

    int deleteByPrimaryKey(OrganizationMenu record);

    int updateByPrimaryKey(OrganizationMenu record);

    int updateByPrimaryKeySelective(OrganizationMenu record);

    int insert(OrganizationMenu record);

    int insertSelective(OrganizationMenu record);

    int insertByBatch(List<OrganizationMenu> list);

    List<OrganizationMenu> select(Page<OrganizationMenu> record);

    long count(Page<OrganizationMenu> record);

}
