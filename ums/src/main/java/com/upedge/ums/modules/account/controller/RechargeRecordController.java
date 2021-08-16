package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.RechargeRecord;
import com.upedge.ums.modules.account.service.RechargeRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.RechargeRecordAddRequest;
import com.upedge.ums.modules.account.request.RechargeRecordListRequest;
import com.upedge.ums.modules.account.request.RechargeRecordUpdateRequest;

import com.upedge.ums.modules.account.response.RechargeRecordAddResponse;
import com.upedge.ums.modules.account.response.RechargeRecordDelResponse;
import com.upedge.ums.modules.account.response.RechargeRecordInfoResponse;
import com.upedge.ums.modules.account.response.RechargeRecordListResponse;
import com.upedge.ums.modules.account.response.RechargeRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/rechargeRecord")
public class RechargeRecordController {
    @Autowired
    private RechargeRecordService rechargeRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:rechargerecord:info:id")
    public RechargeRecordInfoResponse info(@PathVariable Integer id) {
        RechargeRecord result = rechargeRecordService.selectByPrimaryKey(id);
        RechargeRecordInfoResponse res = new RechargeRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerecord:list")
    public RechargeRecordListResponse list(@RequestBody @Valid RechargeRecordListRequest request) {
        List<RechargeRecord> results = rechargeRecordService.select(request);
        Long total = rechargeRecordService.count(request);
        request.setTotal(total);
        RechargeRecordListResponse res = new RechargeRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerecord:add")
    public RechargeRecordAddResponse add(@RequestBody @Valid RechargeRecordAddRequest request) {
        RechargeRecord entity=request.toRechargeRecord();
        rechargeRecordService.insertSelective(entity);
        RechargeRecordAddResponse res = new RechargeRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerecord:del:id")
    public RechargeRecordDelResponse del(@PathVariable Integer id) {
        rechargeRecordService.deleteByPrimaryKey(id);
        RechargeRecordDelResponse res = new RechargeRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerecord:update")
    public RechargeRecordUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid RechargeRecordUpdateRequest request) {
        RechargeRecord entity=request.toRechargeRecord(id);
        rechargeRecordService.updateByPrimaryKeySelective(entity);
        RechargeRecordUpdateResponse res = new RechargeRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
