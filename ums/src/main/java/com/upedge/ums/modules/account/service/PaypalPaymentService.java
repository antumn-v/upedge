package com.upedge.ums.modules.account.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.account.PaypalPayment;

import java.util.List;

public interface PaypalPaymentService {


    PaypalPayment selectByPaymentId(String paymentId);

    List<PaypalPayment> select(Page<PaypalPayment> page);

    int updateByPrimaryKeySelective(PaypalPayment record);

    int insertByBatch(List<PaypalPayment> list);
}
