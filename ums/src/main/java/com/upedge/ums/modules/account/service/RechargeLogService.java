package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.RechargeLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RechargeLogService{

    RechargeLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeLog record);

    int updateByPrimaryKeySelective(RechargeLog record);

    int insert(RechargeLog record);

    int insertSelective(RechargeLog record);

    List<RechargeLog> select(Page<RechargeLog> record);

    long count(Page<RechargeLog> record);
}

