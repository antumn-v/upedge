package com.upedge.ums.modules.store.dao;

import com.upedge.ums.modules.store.entity.StoreAttr;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface StoreAttrDao{

    StoreAttr selectByPrimaryKey(StoreAttr record);

    int deleteByPrimaryKey(StoreAttr record);

    int updateByPrimaryKey(StoreAttr record);

    int updateByPrimaryKeySelective(StoreAttr record);

    int insert(StoreAttr record);

    int insertSelective(StoreAttr record);

    int insertByBatch(List<StoreAttr> list);

    List<StoreAttr> select(Page<StoreAttr> record);

    long count(Page<StoreAttr> record);

}
