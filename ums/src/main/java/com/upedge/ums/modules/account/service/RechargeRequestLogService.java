package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface RechargeRequestLogService{

    RechargeRequestLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeRequestLog record);

    int updateByPrimaryKeySelective(RechargeRequestLog record);

    int insert(RechargeRequestLog record);

    int insertSelective(RechargeRequestLog record);

    List<RechargeRequestLog> select(Page<RechargeRequestLog> record);

    long count(Page<RechargeRequestLog> record);
}

