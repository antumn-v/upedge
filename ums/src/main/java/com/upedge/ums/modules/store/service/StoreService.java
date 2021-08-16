package com.upedge.ums.modules.store.service;

import com.upedge.ums.modules.store.entity.Store;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface StoreService{

    Store selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Store record);

    int updateByPrimaryKeySelective(Store record);

    int insert(Store record);

    int insertSelective(Store record);

    List<Store> select(Page<Store> record);

    long count(Page<Store> record);
}

