package com.upedge.ums.modules.store.dao;

import com.upedge.ums.modules.store.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface StoreDao{

    Store selectByPrimaryKey(Store record);

    int deleteByPrimaryKey(Store record);

    int updateByPrimaryKey(Store record);

    int updateByPrimaryKeySelective(Store record);

    int insert(Store record);

    int insertSelective(Store record);

    int insertByBatch(List<Store> list);

    List<Store> select(Page<Store> record);

    long count(Page<Store> record);

}
