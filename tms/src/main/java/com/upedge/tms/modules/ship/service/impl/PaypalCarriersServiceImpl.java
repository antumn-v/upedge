package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.tms.modules.ship.dao.PaypalCarriersDao;
import com.upedge.tms.modules.ship.entity.PaypalCarriers;
import com.upedge.tms.modules.ship.response.PaypalCarriersListResponse;
import com.upedge.tms.modules.ship.service.PaypalCarriersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PaypalCarriersServiceImpl implements PaypalCarriersService {

    @Autowired
    private PaypalCarriersDao paypalCarriersDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(String enumeratedValue) {
        PaypalCarriers record = new PaypalCarriers();
        record.setEnumeratedValue(enumeratedValue);
        return paypalCarriersDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PaypalCarriers record) {
        return paypalCarriersDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PaypalCarriers record) {
        return paypalCarriersDao.insert(record);
    }

    /**
     *
     */
    public PaypalCarriers selectByPrimaryKey(String enumeratedValue){
        PaypalCarriers record = new PaypalCarriers();
        record.setEnumeratedValue(enumeratedValue);
        return paypalCarriersDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PaypalCarriers record) {
        return paypalCarriersDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PaypalCarriers record) {
        return paypalCarriersDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PaypalCarriers> select(Page<PaypalCarriers> record){
        record.initFromNum();
        return paypalCarriersDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PaypalCarriers> record){
        return paypalCarriersDao.count(record);
    }

    @Override
    public PaypalCarriersListResponse allPaypalCarriers() {
        List<PaypalCarriers> paypalCarriersList=paypalCarriersDao.allPaypalCarriers();
        return new PaypalCarriersListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,paypalCarriersList);
    }

}