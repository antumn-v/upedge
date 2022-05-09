package com.upedge.ums.modules.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import com.upedge.ums.modules.user.service.CustomerVipRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.user.request.CustomerVipRecordAddRequest;
import com.upedge.ums.modules.user.request.CustomerVipRecordListRequest;
import com.upedge.ums.modules.user.request.CustomerVipRecordUpdateRequest;

import com.upedge.ums.modules.user.response.CustomerVipRecordAddResponse;
import com.upedge.ums.modules.user.response.CustomerVipRecordDelResponse;
import com.upedge.ums.modules.user.response.CustomerVipRecordInfoResponse;
import com.upedge.ums.modules.user.response.CustomerVipRecordListResponse;
import com.upedge.ums.modules.user.response.CustomerVipRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerVipRecord")
public class CustomerVipRecordController {
    @Autowired
    private CustomerVipRecordService customerVipRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customerviprecord:info:id")
    public CustomerVipRecordInfoResponse info(@PathVariable Integer id) {
        CustomerVipRecord result = customerVipRecordService.selectByPrimaryKey(id);
        CustomerVipRecordInfoResponse res = new CustomerVipRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:list")
    public CustomerVipRecordListResponse list(@RequestBody @Valid CustomerVipRecordListRequest request) {
        List<CustomerVipRecord> results = customerVipRecordService.select(request);
        Long total = customerVipRecordService.count(request);
        request.setTotal(total);
        CustomerVipRecordListResponse res = new CustomerVipRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:add")
    public CustomerVipRecordAddResponse add(@RequestBody @Valid CustomerVipRecordAddRequest request) {
        CustomerVipRecord entity=request.toCustomerVipRecord();
        customerVipRecordService.insertSelective(entity);
        CustomerVipRecordAddResponse res = new CustomerVipRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:del:id")
    public CustomerVipRecordDelResponse del(@PathVariable Integer id) {
        customerVipRecordService.deleteByPrimaryKey(id);
        CustomerVipRecordDelResponse res = new CustomerVipRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:update")
    public CustomerVipRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid CustomerVipRecordUpdateRequest request) {
        CustomerVipRecord entity=request.toCustomerVipRecord(id);
        customerVipRecordService.updateByPrimaryKeySelective(entity);
        CustomerVipRecordUpdateResponse res = new CustomerVipRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
