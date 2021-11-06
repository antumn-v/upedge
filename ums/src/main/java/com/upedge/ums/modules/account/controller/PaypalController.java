package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.ums.modules.account.service.PaypalPaymentService;
import com.upedge.ums.modules.account.service.PaypalService;
import com.upedge.ums.modules.account.service.RechargeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/paypal")
@RestController
public class PaypalController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    RechargeService rechargeService;

    @Autowired
    PaypalPaymentService paypalPaymentService;

    @PostMapping("/order/url")
    public BaseResponse getPaypalOrderUrl(@RequestBody PaypalOrder paypalOrder) {
        String url = paypalService.getPaypalOrderUrl(paypalOrder);
        if (StringUtils.isNotBlank(url)) {
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, url);
        }
        return BaseResponse.failed("create order failed");
    }

    /**
     * paypal验证 支付后看是否支付成功需要调用此接口
     * @param request
     * @return
     */
    @PostMapping("/order/execute")
    public BaseResponse executePaypalOrder(@RequestBody PaypalExecuteRequest request){

        PaypalPayment paypalPayment = paypalPaymentService.selectByPaymentId(request.getPaymentId());

        if(null != paypalPayment){
            return BaseResponse.failed("Repeat request");
        }

        PaypalPayment payment =  paypalService.executePayment(request);
        if(null == payment){
            return BaseResponse.failed("paypal error");
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,payment);
    }

}
