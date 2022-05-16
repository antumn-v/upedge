package com.upedge.ums.modules.manager.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.manager.request.CustomerManagerAddRequest;
import com.upedge.ums.modules.manager.request.CustomerManagerListRequest;
import com.upedge.ums.modules.manager.request.CustomerManagerUpdateRequest;

import com.upedge.ums.modules.manager.response.CustomerManagerAddResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerDelResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerInfoResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerListResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerManager")
public class CustomerManagerController {
    @Autowired
    private CustomerManagerService customerManagerService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "manager:customermanager:info:id")
    public CustomerManagerInfoResponse info(@PathVariable Long id) {
        CustomerManager result = customerManagerService.selectByPrimaryKey(id);
        CustomerManagerInfoResponse res = new CustomerManagerInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:customermanager:list")
    public CustomerManagerListResponse list(@RequestBody @Valid CustomerManagerListRequest request) {
        List<CustomerManager> results = customerManagerService.select(request);
        Long total = customerManagerService.count(request);
        request.setTotal(total);
        CustomerManagerListResponse res = new CustomerManagerListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "manager:customermanager:add")
    public CustomerManagerAddResponse add(@RequestBody @Valid CustomerManagerAddRequest request) {
        CustomerManager entity=request.toCustomerManager();
        customerManagerService.insertSelective(entity);
        CustomerManagerAddResponse res = new CustomerManagerAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:customermanager:del:id")
    public CustomerManagerDelResponse del(@PathVariable Long id) {
        customerManagerService.deleteByPrimaryKey(id);
        CustomerManagerDelResponse res = new CustomerManagerDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:customermanager:update")
    public CustomerManagerUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerManagerUpdateRequest request) {
        CustomerManager entity=request.toCustomerManager(id);
        customerManagerService.updateByPrimaryKeySelective(entity);
        CustomerManagerUpdateResponse res = new CustomerManagerUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
