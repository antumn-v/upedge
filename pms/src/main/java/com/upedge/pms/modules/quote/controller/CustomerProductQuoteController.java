package com.upedge.pms.modules.quote.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteAddRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;

import com.upedge.pms.modules.quote.response.CustomerProductQuoteAddResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteDelResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteInfoResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteListResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerProductQuote")
public class CustomerProductQuoteController {
    @Autowired
    private CustomerProductQuoteService customerProductQuoteService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "quote:customerproductquote:info:id")
    public CustomerProductQuoteInfoResponse info(@PathVariable Long id) {
        CustomerProductQuote result = customerProductQuoteService.selectByPrimaryKey(id);
        CustomerProductQuoteInfoResponse res = new CustomerProductQuoteInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "quote:customerproductquote:list")
    public CustomerProductQuoteListResponse list(@RequestBody @Valid CustomerProductQuoteListRequest request) {
        List<CustomerProductQuote> results = customerProductQuoteService.select(request);
        Long total = customerProductQuoteService.count(request);
        request.setTotal(total);
        CustomerProductQuoteListResponse res = new CustomerProductQuoteListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "quote:customerproductquote:add")
    public CustomerProductQuoteAddResponse add(@RequestBody @Valid CustomerProductQuoteAddRequest request) {
        CustomerProductQuote entity=request.toCustomerProductQuote();
        customerProductQuoteService.insertSelective(entity);
        CustomerProductQuoteAddResponse res = new CustomerProductQuoteAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:customerproductquote:del:id")
    public CustomerProductQuoteDelResponse del(@PathVariable Long id) {
        customerProductQuoteService.deleteByPrimaryKey(id);
        CustomerProductQuoteDelResponse res = new CustomerProductQuoteDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "quote:customerproductquote:update")
    public CustomerProductQuoteUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerProductQuoteUpdateRequest request) {
        CustomerProductQuote entity=request.toCustomerProductQuote(id);
        customerProductQuoteService.updateByPrimaryKeySelective(entity);
        CustomerProductQuoteUpdateResponse res = new CustomerProductQuoteUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
