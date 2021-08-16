package com.upedge.ums.modules.address.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import com.upedge.ums.modules.address.service.CustomerAddressService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.address.request.CustomerAddressAddRequest;
import com.upedge.ums.modules.address.request.CustomerAddressListRequest;
import com.upedge.ums.modules.address.request.CustomerAddressUpdateRequest;

import com.upedge.ums.modules.address.response.CustomerAddressAddResponse;
import com.upedge.ums.modules.address.response.CustomerAddressDelResponse;
import com.upedge.ums.modules.address.response.CustomerAddressInfoResponse;
import com.upedge.ums.modules.address.response.CustomerAddressListResponse;
import com.upedge.ums.modules.address.response.CustomerAddressUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerAddress")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService customerAddressService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "address:customeraddress:info:id")
    public CustomerAddressInfoResponse info(@PathVariable Long id) {
        CustomerAddress result = customerAddressService.selectByPrimaryKey(id);
        CustomerAddressInfoResponse res = new CustomerAddressInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:list")
    public CustomerAddressListResponse list(@RequestBody @Valid CustomerAddressListRequest request) {
        List<CustomerAddress> results = customerAddressService.select(request);
        Long total = customerAddressService.count(request);
        request.setTotal(total);
        CustomerAddressListResponse res = new CustomerAddressListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:add")
    public CustomerAddressAddResponse add(@RequestBody @Valid CustomerAddressAddRequest request) {
        CustomerAddress entity=request.toCustomerAddress();
        customerAddressService.insertSelective(entity);
        CustomerAddressAddResponse res = new CustomerAddressAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:del:id")
    public CustomerAddressDelResponse del(@PathVariable Long id) {
        customerAddressService.deleteByPrimaryKey(id);
        CustomerAddressDelResponse res = new CustomerAddressDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "address:customeraddress:update")
    public CustomerAddressUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerAddressUpdateRequest request) {
        CustomerAddress entity=request.toCustomerAddress(id);
        customerAddressService.updateByPrimaryKeySelective(entity);
        CustomerAddressUpdateResponse res = new CustomerAddressUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
