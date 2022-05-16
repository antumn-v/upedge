package com.upedge.ums.modules.manager.dao;

import com.upedge.ums.modules.manager.entity.ManagerComissionRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ManagerComissionRecordDao{

    ManagerComissionRecord selectByPrimaryKey(ManagerComissionRecord record);

    int deleteByPrimaryKey(ManagerComissionRecord record);

    int updateByPrimaryKey(ManagerComissionRecord record);

    int updateByPrimaryKeySelective(ManagerComissionRecord record);

    int insert(ManagerComissionRecord record);

    int insertSelective(ManagerComissionRecord record);

    int insertByBatch(List<ManagerComissionRecord> list);

    List<ManagerComissionRecord> select(Page<ManagerComissionRecord> record);

    long count(Page<ManagerComissionRecord> record);

}
