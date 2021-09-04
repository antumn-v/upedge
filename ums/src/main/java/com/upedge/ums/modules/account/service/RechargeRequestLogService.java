package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.request.RejectRechargeRequest;
import com.upedge.ums.modules.account.request.TransferRechargeRequest;

import java.util.List;

/**
 * @author gx
 */
public interface RechargeRequestLogService{

    BaseResponse transferRechargeRequest(TransferRechargeRequest rechargeRequest, Session session);

    BaseResponse confirmRechargeRequest(Long id,Session session);

    BaseResponse rejectRechargeRequest(RejectRechargeRequest request, Session session);

    RechargeRequestLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(RechargeRequestLog record);

    int updateByPrimaryKeySelective(RechargeRequestLog record);

    int insert(RechargeRequestLog record);

    int insertSelective(RechargeRequestLog record);

    List<RechargeRequestLog> select(Page<RechargeRequestLog> record);

    long count(Page<RechargeRequestLog> record);
}

