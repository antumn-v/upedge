package com.upedge.ums.modules.user.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import com.upedge.ums.modules.user.request.CustomerVipRebateRecordAddRequest;
import com.upedge.ums.modules.user.request.CustomerVipRebateRecordListRequest;
import com.upedge.ums.modules.user.response.CustomerVipRebateRecordAddResponse;
import com.upedge.ums.modules.user.response.CustomerVipRebateRecordInfoResponse;
import com.upedge.ums.modules.user.response.CustomerVipRebateRecordListResponse;
import com.upedge.ums.modules.user.service.CustomerVipRebateRecordService;
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
@RequestMapping("/customerVipRebateRecord")
public class CustomerVipRebateRecordController {
    @Autowired
    private CustomerVipRebateRecordService customerVipRebateRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customerviprebaterecord:info:id")
    public CustomerVipRebateRecordInfoResponse info(@PathVariable Long id) {
        CustomerVipRebateRecord result = customerVipRebateRecordService.selectByPrimaryKey(id);
        CustomerVipRebateRecordInfoResponse res = new CustomerVipRebateRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprebaterecord:list")
    public CustomerVipRebateRecordListResponse list(@RequestBody @Valid CustomerVipRebateRecordListRequest request) {
        List<CustomerVipRebateRecord> results = customerVipRebateRecordService.select(request);
        Long total = customerVipRebateRecordService.count(request);
        request.setTotal(total);
        CustomerVipRebateRecordListResponse res = new CustomerVipRebateRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprebaterecord:add")
    public CustomerVipRebateRecordAddResponse add(@RequestBody @Valid CustomerVipRebateRecordAddRequest request) {
        CustomerVipRebateRecord entity=request.toCustomerVipRebateRecord();
        customerVipRebateRecordService.insertSelective(entity);
        CustomerVipRebateRecordAddResponse res = new CustomerVipRebateRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }



}
