package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;

import java.util.List;

/**
 * @author gx
 */
public interface ManagerCommissionRecordDao{

    ManagerCommissionRecord selectByPrimaryKey(ManagerCommissionRecord record);

    int deleteByPrimaryKey(ManagerCommissionRecord record);

    int updateByPrimaryKey(ManagerCommissionRecord record);

    int updateByPrimaryKeySelective(ManagerCommissionRecord record);

    int insert(ManagerCommissionRecord record);

    int insertSelective(ManagerCommissionRecord record);

    int insertByBatch(List<ManagerCommissionRecord> list);

    List<ManagerCommissionRecord> select(Page<ManagerCommissionRecord> record);

    long count(Page<ManagerCommissionRecord> record);

}
