package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.PaypalPayment;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PaypalPaymentService{

    PaypalPayment selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PaypalPayment record);

    int updateByPrimaryKeySelective(PaypalPayment record);

    int insert(PaypalPayment record);

    int insertSelective(PaypalPayment record);

    List<PaypalPayment> select(Page<PaypalPayment> record);

    long count(Page<PaypalPayment> record);
}

