package com.upedge.pms.modules.category.service;

import com.upedge.pms.modules.category.entity.AliCnCategory;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AliCnCategoryService{

    AliCnCategory selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(AliCnCategory record);

    int updateByPrimaryKeySelective(AliCnCategory record);

    int insert(AliCnCategory record);

    int insertSelective(AliCnCategory record);

    List<AliCnCategory> select(Page<AliCnCategory> record);

    long count(Page<AliCnCategory> record);
}

