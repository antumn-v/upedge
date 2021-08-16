package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.RechargeRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RechargeRecordService{

    RechargeRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(RechargeRecord record);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    List<RechargeRecord> select(Page<RechargeRecord> record);

    long count(Page<RechargeRecord> record);
}

