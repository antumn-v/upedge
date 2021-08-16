package com.upedge.ums.modules.account.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.account.dao.PaymentLogDao;
import com.upedge.ums.modules.account.entity.PaymentLog;
import com.upedge.ums.modules.account.service.PaymentLogService;


@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    @Autowired
    private PaymentLogDao paymentLogDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PaymentLog record = new PaymentLog();
        record.setId(id);
        return paymentLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PaymentLog record) {
        return paymentLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PaymentLog record) {
        return paymentLogDao.insert(record);
    }

    /**
     *
     */
    public PaymentLog selectByPrimaryKey(Long id){
        PaymentLog record = new PaymentLog();
        record.setId(id);
        return paymentLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PaymentLog record) {
        return paymentLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PaymentLog record) {
        return paymentLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PaymentLog> select(Page<PaymentLog> record){
        record.initFromNum();
        return paymentLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PaymentLog> record){
        return paymentLogDao.count(record);
    }

}