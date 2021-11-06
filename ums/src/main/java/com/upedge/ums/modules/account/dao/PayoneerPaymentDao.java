package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;

import java.util.List;

/**
 * @author author
 */
public interface PayoneerPaymentDao{

    PayoneerPayment selectByPrimaryKey(PayoneerPayment record);

    int deleteByPrimaryKey(PayoneerPayment record);

    int updateByPrimaryKey(PayoneerPayment record);

    int updateByPrimaryKeySelective(PayoneerPayment record);

    int insert(PayoneerPayment record);

    int insertSelective(PayoneerPayment record);

    int insertByBatch(List<PayoneerPayment> list);

    List<PayoneerPayment> select(Page<PayoneerPayment> record);

    long count(Page<PayoneerPayment> record);

}
