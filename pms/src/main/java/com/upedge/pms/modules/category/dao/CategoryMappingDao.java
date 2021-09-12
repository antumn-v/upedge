package com.upedge.pms.modules.category.dao;

import com.upedge.pms.modules.category.entity.CategoryMapping;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CategoryMappingDao{

    CategoryMapping selectByAliCateCode(String aliCnCategoryId);

    CategoryMapping selectByPrimaryKey(CategoryMapping record);

    int deleteByPrimaryKey(CategoryMapping record);

    int updateByPrimaryKey(CategoryMapping record);

    int updateByPrimaryKeySelective(CategoryMapping record);

    int insert(CategoryMapping record);

    int insertSelective(CategoryMapping record);

    int insertByBatch(List<CategoryMapping> list);

    List<CategoryMapping> select(Page<CategoryMapping> record);

    long count(Page<CategoryMapping> record);

}
