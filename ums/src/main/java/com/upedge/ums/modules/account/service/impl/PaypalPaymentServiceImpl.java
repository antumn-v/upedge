package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.PaypalPaymentDao;
import com.upedge.ums.modules.account.entity.PaypalPayment;
import com.upedge.ums.modules.account.service.PaypalPaymentService;


@Service
public class PaypalPaymentServiceImpl implements PaypalPaymentService {

    @Autowired
    private PaypalPaymentDao paypalPaymentDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PaypalPayment record = new PaypalPayment();
        record.setId(id);
        return paypalPaymentDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PaypalPayment record) {
        return paypalPaymentDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PaypalPayment record) {
        return paypalPaymentDao.insert(record);
    }

    /**
     *
     */
    public PaypalPayment selectByPrimaryKey(Long id){
        PaypalPayment record = new PaypalPayment();
        record.setId(id);
        return paypalPaymentDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PaypalPayment record) {
        return paypalPaymentDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PaypalPayment record) {
        return paypalPaymentDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PaypalPayment> select(Page<PaypalPayment> record){
        record.initFromNum();
        return paypalPaymentDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PaypalPayment> record){
        return paypalPaymentDao.count(record);
    }

}