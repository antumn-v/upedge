package com.upedge.oms.modules.pack.service;

import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PackageReplaceRecordService{

    PackageReplaceRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(PackageReplaceRecord record);

    int updateByPrimaryKeySelective(PackageReplaceRecord record);

    int insert(PackageReplaceRecord record);

    int insertSelective(PackageReplaceRecord record);

    List<PackageReplaceRecord> select(Page<PackageReplaceRecord> record);

    long count(Page<PackageReplaceRecord> record);
}

