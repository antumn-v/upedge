package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.PaypalPayment;
import com.upedge.ums.modules.account.service.PaypalPaymentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.PaypalPaymentAddRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentListRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentUpdateRequest;

import com.upedge.ums.modules.account.response.PaypalPaymentAddResponse;
import com.upedge.ums.modules.account.response.PaypalPaymentDelResponse;
import com.upedge.ums.modules.account.response.PaypalPaymentInfoResponse;
import com.upedge.ums.modules.account.response.PaypalPaymentListResponse;
import com.upedge.ums.modules.account.response.PaypalPaymentUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "paypal交易记录，先不用管")
@RestController
@RequestMapping("/paypalPayment")
public class PaypalPaymentController {
    @Autowired
    private PaypalPaymentService paypalPaymentService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:paypalpayment:info:id")
    public PaypalPaymentInfoResponse info(@PathVariable Long id) {
        PaypalPayment result = paypalPaymentService.selectByPrimaryKey(id);
        PaypalPaymentInfoResponse res = new PaypalPaymentInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:paypalpayment:list")
    public PaypalPaymentListResponse list(@RequestBody @Valid PaypalPaymentListRequest request) {
        List<PaypalPayment> results = paypalPaymentService.select(request);
        Long total = paypalPaymentService.count(request);
        request.setTotal(total);
        PaypalPaymentListResponse res = new PaypalPaymentListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:paypalpayment:add")
    public PaypalPaymentAddResponse add(@RequestBody @Valid PaypalPaymentAddRequest request) {
        PaypalPayment entity=request.toPaypalPayment();
        paypalPaymentService.insertSelective(entity);
        PaypalPaymentAddResponse res = new PaypalPaymentAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:paypalpayment:del:id")
    public PaypalPaymentDelResponse del(@PathVariable Long id) {
        paypalPaymentService.deleteByPrimaryKey(id);
        PaypalPaymentDelResponse res = new PaypalPaymentDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:paypalpayment:update")
    public PaypalPaymentUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PaypalPaymentUpdateRequest request) {
        PaypalPayment entity=request.toPaypalPayment(id);
        paypalPaymentService.updateByPrimaryKeySelective(entity);
        PaypalPaymentUpdateResponse res = new PaypalPaymentUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
