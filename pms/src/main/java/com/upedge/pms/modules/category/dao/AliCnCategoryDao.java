package com.upedge.pms.modules.category.dao;

import com.upedge.pms.modules.category.entity.AliCnCategory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AliCnCategoryDao{

    AliCnCategory selectByPrimaryKey(AliCnCategory record);

    int deleteByPrimaryKey(AliCnCategory record);

    int updateByPrimaryKey(AliCnCategory record);

    int updateByPrimaryKeySelective(AliCnCategory record);

    int insert(AliCnCategory record);

    int insertSelective(AliCnCategory record);

    int insertByBatch(List<AliCnCategory> list);

    List<AliCnCategory> select(Page<AliCnCategory> record);

    long count(Page<AliCnCategory> record);

}
