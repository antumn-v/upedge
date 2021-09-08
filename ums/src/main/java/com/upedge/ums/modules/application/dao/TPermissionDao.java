package com.upedge.ums.modules.application.dao;

import com.upedge.ums.modules.application.entity.TPermission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface TPermissionDao{

    TPermission selectByPrimaryKey(TPermission record);

    int deleteByPrimaryKey(TPermission record);

    int updateByPrimaryKey(TPermission record);

    int updateByPrimaryKeySelective(TPermission record);

    int insert(TPermission record);

    int insertSelective(TPermission record);

    int insertByBatch(List<TPermission> list);

    List<TPermission> select(Page<TPermission> record);

    long count(Page<TPermission> record);

}
