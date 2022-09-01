package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.enums.CustomerSettingEnum;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.request.CustomerSettingAddRequest;
import com.upedge.ums.modules.user.request.CustomerSettingListRequest;
import com.upedge.ums.modules.user.request.CustomerSettingUpdateRequest;
import com.upedge.ums.modules.user.response.*;
import com.upedge.ums.modules.user.service.CustomerSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户设置")
@RestController
@RequestMapping("/customerSetting")
public class CustomerSettingController {
    @Autowired
    private CustomerSettingService customerSettingService;

    @ApiOperation("可设置的值")
    @GetMapping("/values")
    public BaseResponse settingValues(){
        List<CustomerSetting> customerSettings = new ArrayList<>();
        CustomerSettingEnum[] customerSettingEnums = CustomerSettingEnum.values();
        for (CustomerSettingEnum customerSettingEnum : customerSettingEnums) {
            CustomerSetting customerSetting = new CustomerSetting();
            customerSetting.setSettingName(customerSettingEnum.name());
            customerSetting.setSettingValue(customerSettingEnum.getValue());
            customerSettings.add(customerSetting);
        }
        return BaseResponse.success(customerSettings);
    }


    @PostMapping("/saveDefaultSetting")
    public BaseResponse saveDefaultSetting(){
        customerSettingService.saveNewSetting();
        return BaseResponse.success();
    }


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

    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "user:customersetting:update")
    public CustomerSettingUpdateResponse update(@RequestBody @Valid CustomerSettingUpdateRequest request) {
        CustomerSetting entity=request.toCustomerSetting();
        customerSettingService.updateByPrimaryKeySelective(entity);
        CustomerSettingUpdateResponse res = new CustomerSettingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
