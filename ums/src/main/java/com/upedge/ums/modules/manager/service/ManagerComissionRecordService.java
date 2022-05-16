package com.upedge.ums.modules.manager.service;

import com.upedge.ums.modules.manager.entity.ManagerComissionRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ManagerComissionRecordService{

    ManagerComissionRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerComissionRecord record);

    int updateByPrimaryKeySelective(ManagerComissionRecord record);

    int insert(ManagerComissionRecord record);

    int insertSelective(ManagerComissionRecord record);

    List<ManagerComissionRecord> select(Page<ManagerComissionRecord> record);

    long count(Page<ManagerComissionRecord> record);
}

