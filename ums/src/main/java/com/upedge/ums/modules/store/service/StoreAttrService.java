package com.upedge.ums.modules.store.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.StoreAttr;

import java.util.List;

/**
 * @author author
 */
public interface StoreAttrService{

    StoreAttr selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(StoreAttr record);

    int updateByPrimaryKeySelective(StoreAttr record);

    int insert(StoreAttr record);

    int insertSelective(StoreAttr record);

    List<StoreAttr> select(Page<StoreAttr> record);

    long count(Page<StoreAttr> record);
}

