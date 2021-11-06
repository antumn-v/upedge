package com.upedge.ums.modules.account.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.ums.modules.account.dao.PayoneerPaymentDao;
import com.upedge.ums.modules.account.dao.PaypalPaymentDao;
import com.upedge.ums.modules.account.entity.payoneer.PayoneerPayment;
import com.upedge.ums.modules.account.request.PayoneerPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentUpdateRemarkRequest;
import com.upedge.ums.modules.account.service.AdminPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminPaymentServiceImpl implements AdminPaymentService {
    @Autowired
    PaypalPaymentDao paypalPaymentDao;
    @Autowired
    PayoneerPaymentDao payoneerPaymentDao;

    @Override
    public BaseResponse payoneerPayments(PayoneerPaymentRequest request) {
        if(request.getT() == null){
            request.setT(new PayoneerPayment());
        }
        request.initFromNum();
        List<PayoneerPayment> list=payoneerPaymentDao.select(request);
        Long total = payoneerPaymentDao.count(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,list,request);
    }

    @Override
    public BaseResponse paypalPayments(PaypalPaymentRequest request) {
        if(request.getT() == null){
            request.setT(new PaypalPayment());
        }
        request.initFromNum();
        List<PaypalPayment> list=paypalPaymentDao.select(request);
        Long total = paypalPaymentDao.count(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,list,request);
    }

    @Override
    @Transactional
    public BaseResponse paypalUpdateRemark(PaypalPaymentUpdateRemarkRequest request) {
        paypalPaymentDao.paypalUpdateRemark(request.getId(),request.getRemark());
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }
}
