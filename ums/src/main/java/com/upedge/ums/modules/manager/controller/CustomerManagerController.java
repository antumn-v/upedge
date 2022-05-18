package com.upedge.ums.modules.manager.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.request.CustomerManagerAddRequest;
import com.upedge.ums.modules.manager.request.CustomerManagerListRequest;
import com.upedge.ums.modules.manager.response.CustomerManagerAddResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerInfoResponse;
import com.upedge.ums.modules.manager.response.CustomerManagerListResponse;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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




}
