package com.upedge.ums.modules.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerSettingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.user.request.CustomerSettingAddRequest;
import com.upedge.ums.modules.user.request.CustomerSettingListRequest;
import com.upedge.ums.modules.user.request.CustomerSettingUpdateRequest;

import com.upedge.ums.modules.user.response.CustomerSettingAddResponse;
import com.upedge.ums.modules.user.response.CustomerSettingDelResponse;
import com.upedge.ums.modules.user.response.CustomerSettingInfoResponse;
import com.upedge.ums.modules.user.response.CustomerSettingListResponse;
import com.upedge.ums.modules.user.response.CustomerSettingUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerSetting")
public class CustomerSettingController {
    @Autowired
    private CustomerSettingService customerSettingService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customersetting:info:id")
    public CustomerSettingInfoResponse info(@PathVariable Integer id) {
        CustomerSetting result = customerSettingService.selectByPrimaryKey(id);
        CustomerSettingInfoResponse res = new CustomerSettingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customersetting:list")
    public CustomerSettingListResponse list(@RequestBody @Valid CustomerSettingListRequest request) {
        List<CustomerSetting> results = customerSettingService.select(request);
        Long total = customerSettingService.count(request);
        request.setTotal(total);
        CustomerSettingListResponse res = new CustomerSettingListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customersetting:add")
    public CustomerSettingAddResponse add(@RequestBody @Valid CustomerSettingAddRequest request) {
        CustomerSetting entity=request.toCustomerSetting();
        customerSettingService.insertSelective(entity);
        CustomerSettingAddResponse res = new CustomerSettingAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customersetting:del:id")
    public CustomerSettingDelResponse del(@PathVariable Integer id) {
        customerSettingService.deleteByPrimaryKey(id);
        CustomerSettingDelResponse res = new CustomerSettingDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customersetting:update")
    public CustomerSettingUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid CustomerSettingUpdateRequest request) {
        CustomerSetting entity=request.toCustomerSetting(id);
        customerSettingService.updateByPrimaryKeySelective(entity);
        CustomerSettingUpdateResponse res = new CustomerSettingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
