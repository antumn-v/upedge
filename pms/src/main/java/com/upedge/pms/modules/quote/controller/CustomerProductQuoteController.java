package com.upedge.pms.modules.quote.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户报价产品管理")
@RestController
@RequestMapping("/customerProductQuote")
public class CustomerProductQuoteController {
    @Autowired
    private CustomerProductQuoteService customerProductQuoteService;


    @ApiOperation("已报价产品列表")
    @PostMapping("/list")
    public BaseResponse customerProductQuoteList(@RequestBody CustomerProductQuoteListRequest request){
        List<CustomerProductQuote> customerProductQuotes = customerProductQuoteService.select(request);
        Long total = customerProductQuoteService.count(request);
        request.setTotal(total);
        return BaseResponse.success(customerProductQuotes,request);
    }

    @ApiOperation("修改客户报价")
    @PostMapping("/update")
    public BaseResponse updateCustomerProductQuote(@RequestBody@Valid CustomerProductQuoteUpdateRequest request){
        BaseResponse baseResponse = customerProductQuoteService.updateCustomerProductQuote(request);
//        if (baseResponse.getCode() == ResultCode.SUCCESS_CODE){
//            List<Long> storeVariantIds = new ArrayList<>();
//            storeVariantIds.add(request.getStoreVariantId());
//            customerProductQuoteService.sendCustomerProductQuoteUpdateMessage(storeVariantIds);
//        }
        return baseResponse;
    }

    @PostMapping("/search")
    public List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectQuoteDetail(request);
        return customerProductQuoteVos;
    }



}
