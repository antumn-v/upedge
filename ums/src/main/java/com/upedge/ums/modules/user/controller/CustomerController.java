package com.upedge.ums.modules.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.user.request.CustomerAddRequest;
import com.upedge.ums.modules.user.request.CustomerListRequest;
import com.upedge.ums.modules.user.request.CustomerUpdateRequest;

import com.upedge.ums.modules.user.response.CustomerAddResponse;
import com.upedge.ums.modules.user.response.CustomerDelResponse;
import com.upedge.ums.modules.user.response.CustomerInfoResponse;
import com.upedge.ums.modules.user.response.CustomerListResponse;
import com.upedge.ums.modules.user.response.CustomerUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customer:info:id")
    public CustomerInfoResponse info(@PathVariable Long id) {
        Customer result = customerService.selectByPrimaryKey(id);
        CustomerInfoResponse res = new CustomerInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customer:list")
    public CustomerListResponse list(@RequestBody @Valid CustomerListRequest request) {
        List<Customer> results = customerService.select(request);
        Long total = customerService.count(request);
        request.setTotal(total);
        CustomerListResponse res = new CustomerListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customer:add")
    public CustomerAddResponse add(@RequestBody @Valid CustomerAddRequest request) {
        Customer entity=request.toCustomer();
        customerService.insertSelective(entity);
        CustomerAddResponse res = new CustomerAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customer:del:id")
    public CustomerDelResponse del(@PathVariable Long id) {
        customerService.deleteByPrimaryKey(id);
        CustomerDelResponse res = new CustomerDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customer:update")
    public CustomerUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerUpdateRequest request) {
        Customer entity=request.toCustomer(id);
        customerService.updateByPrimaryKeySelective(entity);
        CustomerUpdateResponse res = new CustomerUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
