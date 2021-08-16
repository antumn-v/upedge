package com.upedge.ums.modules.organization.dao;

import com.upedge.ums.modules.organization.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrganizationDao{

    Organization selectByPrimaryKey(Organization record);

    int deleteByPrimaryKey(Organization record);

    int updateByPrimaryKey(Organization record);

    int updateByPrimaryKeySelective(Organization record);

    int insert(Organization record);

    int insertSelective(Organization record);

    int insertByBatch(List<Organization> list);

    List<Organization> select(Page<Organization> record);

    long count(Page<Organization> record);

}
