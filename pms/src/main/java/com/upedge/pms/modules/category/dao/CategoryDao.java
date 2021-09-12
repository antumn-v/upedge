package com.upedge.pms.modules.category.dao;

import com.upedge.pms.modules.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CategoryDao{

    Category selectByPrimaryKey(Category record);

    int deleteByPrimaryKey(Category record);

    int updateByPrimaryKey(Category record);

    int updateByPrimaryKeySelective(Category record);

    int insert(Category record);

    int insertSelective(Category record);

    int insertByBatch(List<Category> list);

    List<Category> select(Page<Category> record);

    long count(Page<Category> record);

}
