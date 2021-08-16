package com.upedge.ums.modules.store.dao;

import com.upedge.ums.modules.store.entity.StoreSetting;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface StoreSettingDao{

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
