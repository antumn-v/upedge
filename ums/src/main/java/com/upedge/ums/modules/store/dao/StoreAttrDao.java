package com.upedge.ums.modules.store.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.StoreAttr;

import java.util.List;

/**
 * @author author
 */
public interface StoreAttrDao{

    List<StoreAttr> selectStoreAttr(StoreAttr attr);

    List<StoreAttr> selectByStore(Long storeId);

    Long deleteByStoreId(Long storeId);

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
