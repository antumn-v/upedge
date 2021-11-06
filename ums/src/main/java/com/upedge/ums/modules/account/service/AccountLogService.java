package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.request.OrderAccountLogRequest;
import com.upedge.common.model.user.vo.OrderAccountLogVo;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.request.AccountLogListRequest;

import java.util.List;

/**
 * @author author
 */
public interface AccountLogService{

    AccountLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(AccountLog record);

    int updateByPrimaryKeySelective(AccountLog record);

    int insert(AccountLog record);

    int insertSelective(AccountLog record);

    List<AccountLog> select(Page<AccountLog> record);

    long count(Page<AccountLog> record);

    BaseResponse orderInfoAccountFlow(Long transactionId);

    BaseResponse accountLogPayInfo(String transactionId);

    List<AccountLog> selectAllByTime(AccountLogListRequest request);

    OrderAccountLogVo selectAccountLogByOrder(OrderAccountLogRequest accountLogQuery);

    OrderAccountLogVo accountLogPayInfoByTransactionDetail(TransactionDetail transactionDetail);
}

