package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.PaymentLog;

import java.util.List;

/**
 * @author author
 */
public interface PaymentLogDao{

    List<PaymentLog> selectPendingPaymentByAccountId(Long accountId);

    PaymentLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(PaymentLog record);

    int updateByPrimaryKey(PaymentLog record);

    int updateByPrimaryKeySelective(PaymentLog record);

    int insert(PaymentLog record);

    int insertSelective(PaymentLog record);

    int insertByBatch(List<PaymentLog> list);

    List<PaymentLog> select(Page<PaymentLog> record);

    long count(Page<PaymentLog> record);

}
