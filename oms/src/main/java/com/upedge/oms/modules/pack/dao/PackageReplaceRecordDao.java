package com.upedge.oms.modules.pack.dao;

import com.upedge.oms.modules.pack.entity.PackageReplaceRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PackageReplaceRecordDao{

    PackageReplaceRecord selectByPrimaryKey(PackageReplaceRecord record);

    int deleteByPrimaryKey(PackageReplaceRecord record);

    int updateByPrimaryKey(PackageReplaceRecord record);

    int updateByPrimaryKeySelective(PackageReplaceRecord record);

    int insert(PackageReplaceRecord record);

    int insertSelective(PackageReplaceRecord record);

    int insertByBatch(List<PackageReplaceRecord> list);

    List<PackageReplaceRecord> select(Page<PackageReplaceRecord> record);

    long count(Page<PackageReplaceRecord> record);

}
