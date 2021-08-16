package com.upedge.ums.modules.store.service;

import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface StoreSettingService{

    StoreSetting selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(StoreSetting record);

    int updateByPrimaryKeySelective(StoreSetting record);

    int insert(StoreSetting record);

    int insertSelective(StoreSetting record);

    List<StoreSetting> select(Page<StoreSetting> record);

    long count(Page<StoreSetting> record);
}

