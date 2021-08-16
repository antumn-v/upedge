package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.PaymentLog;
import com.upedge.ums.modules.account.service.PaymentLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.PaymentLogAddRequest;
import com.upedge.ums.modules.account.request.PaymentLogListRequest;
import com.upedge.ums.modules.account.request.PaymentLogUpdateRequest;

import com.upedge.ums.modules.account.response.PaymentLogAddResponse;
import com.upedge.ums.modules.account.response.PaymentLogDelResponse;
import com.upedge.ums.modules.account.response.PaymentLogInfoResponse;
import com.upedge.ums.modules.account.response.PaymentLogListResponse;
import com.upedge.ums.modules.account.response.PaymentLogUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/paymentLog")
public class PaymentLogController {
    @Autowired
    private PaymentLogService paymentLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:paymentlog:info:id")
    public PaymentLogInfoResponse info(@PathVariable Long id) {
        PaymentLog result = paymentLogService.selectByPrimaryKey(id);
        PaymentLogInfoResponse res = new PaymentLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:paymentlog:list")
    public PaymentLogListResponse list(@RequestBody @Valid PaymentLogListRequest request) {
        List<PaymentLog> results = paymentLogService.select(request);
        Long total = paymentLogService.count(request);
        request.setTotal(total);
        PaymentLogListResponse res = new PaymentLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:paymentlog:add")
    public PaymentLogAddResponse add(@RequestBody @Valid PaymentLogAddRequest request) {
        PaymentLog entity=request.toPaymentLog();
        paymentLogService.insertSelective(entity);
        PaymentLogAddResponse res = new PaymentLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:paymentlog:del:id")
    public PaymentLogDelResponse del(@PathVariable Long id) {
        paymentLogService.deleteByPrimaryKey(id);
        PaymentLogDelResponse res = new PaymentLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:paymentlog:update")
    public PaymentLogUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PaymentLogUpdateRequest request) {
        PaymentLog entity=request.toPaymentLog(id);
        paymentLogService.updateByPrimaryKeySelective(entity);
        PaymentLogUpdateResponse res = new PaymentLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
