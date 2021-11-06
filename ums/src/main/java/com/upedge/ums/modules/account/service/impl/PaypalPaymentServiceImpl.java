package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.ums.modules.account.dao.PaypalPaymentDao;
import com.upedge.ums.modules.account.service.PaypalPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaypalPaymentServiceImpl implements PaypalPaymentService {

    @Autowired
    PaypalPaymentDao paypalPaymentDao;

    @Override
    public PaypalPayment selectByPaymentId(String paymentId) {
        return paypalPaymentDao.selectByPaymentId(paymentId);
    }

    @Override
    public List<PaypalPayment> select(Page<PaypalPayment> page) {

        return paypalPaymentDao.select(page);
    }

    @Override
    public int updateByPrimaryKeySelective(PaypalPayment record) {
        return paypalPaymentDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insertByBatch(List<PaypalPayment> list) {
        return paypalPaymentDao.insertByBatch(list);
    }
}
