package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.account.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

//@RestController
//@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping("/{code}/rate")
    public BaseResponse getCurrencyRate(@PathVariable String code){
        BigDecimal rate = currencyService.selectCnyRateByCode(code);
        if(null != rate){

            return new BaseResponse(ResultCode.SUCCESS_CODE,rate);
        }
        return BaseResponse.failed();
    }
}
