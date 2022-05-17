package com.upedge.ums.modules.manager.service;

import com.upedge.common.model.user.request.ManagerAddCommissionRequest;
import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ManagerCommissionRecordService{

    void addCommissionRecord(ManagerAddCommissionRequest request);

    ManagerCommissionRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerCommissionRecord record);

    int updateByPrimaryKeySelective(ManagerCommissionRecord record);

    int insert(ManagerCommissionRecord record);

    int insertSelective(ManagerCommissionRecord record);

    List<ManagerCommissionRecord> select(Page<ManagerCommissionRecord> record);

    long count(Page<ManagerCommissionRecord> record);
}

