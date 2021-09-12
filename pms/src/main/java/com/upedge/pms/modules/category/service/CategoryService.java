package com.upedge.pms.modules.category.service;

import com.upedge.pms.modules.category.entity.Category;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CategoryService{

    Category selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Category record);

    int updateByPrimaryKeySelective(Category record);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> select(Page<Category> record);

    long count(Page<Category> record);
}

