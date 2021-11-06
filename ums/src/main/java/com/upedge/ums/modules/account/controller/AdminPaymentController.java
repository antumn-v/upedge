package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PayoneerPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentUpdateRemarkRequest;
import com.upedge.ums.modules.account.service.AdminPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/payment")
@RestController
public class AdminPaymentController {

    @Autowired
    AdminPaymentService adminPaymentService;

//    @RequestMapping(value = "/payoneerPayments",method = RequestMethod.POST)
//    public BaseResponse payoneerPayments(@RequestBody PayoneerPaymentRequest request){
//      return adminPaymentService.payoneerPayments(request);
//    }

    @RequestMapping(value = "/paypalPayments",method = RequestMethod.POST)
    public BaseResponse paypalPayments(@RequestBody PaypalPaymentRequest request){
      return adminPaymentService.paypalPayments(request);
    }

//    @RequestMapping(value = "/paypalUpdateRemark",method = RequestMethod.POST)
//    public BaseResponse paypalUpdateRemark(@RequestBody @Valid PaypalPaymentUpdateRemarkRequest request){
//        return adminPaymentService.paypalUpdateRemark(request);
//    }

}
