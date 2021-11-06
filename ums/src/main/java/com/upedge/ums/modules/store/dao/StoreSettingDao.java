package com.upedge.ums.modules.store.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.store.entity.StoreSetting;

import java.util.List;

/**
 * @author author
 */
public interface StoreSettingDao{

    List<StoreSetting> selectByStoreId(Long storeId);

    int updateStoreSettingValueByName(StoreSetting storeSetting);

    StoreSetting selectByPrimaryKey(StoreSetting record);

    int deleteByPrimaryKey(StoreSetting record);

    int updateByPrimaryKey(StoreSetting record);

    int updateByPrimaryKeySelective(StoreSetting record);

    int insert(StoreSetting record);

    int insertSelective(StoreSetting record);

    int insertByBatch(List<StoreSetting> list);

    List<StoreSetting> select(Page<StoreSetting> record);

    long count(Page<StoreSetting> record);

}
