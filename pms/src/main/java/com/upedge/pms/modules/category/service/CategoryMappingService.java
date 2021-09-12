package com.upedge.pms.modules.category.service;

import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CategoryMappingService{

    CategoryMapping selectByAliCateCode(String aliCnCategoryId);

    CategoryMapping selectByPrimaryKey(Long aliCnCategoryId);

    int deleteByPrimaryKey(Long aliCnCategoryId);

    int updateByPrimaryKey(CategoryMapping record);

    int updateByPrimaryKeySelective(CategoryMapping record);

    int insert(CategoryMapping record);

    int insertSelective(CategoryMapping record);

    List<CategoryMapping> select(Page<CategoryMapping> record);

    long count(Page<CategoryMapping> record);
}

