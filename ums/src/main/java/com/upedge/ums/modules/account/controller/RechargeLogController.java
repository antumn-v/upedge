package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.RechargeLog;
import com.upedge.ums.modules.account.service.RechargeLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.RechargeLogAddRequest;
import com.upedge.ums.modules.account.request.RechargeLogListRequest;
import com.upedge.ums.modules.account.request.RechargeLogUpdateRequest;

import com.upedge.ums.modules.account.response.RechargeLogAddResponse;
import com.upedge.ums.modules.account.response.RechargeLogDelResponse;
import com.upedge.ums.modules.account.response.RechargeLogInfoResponse;
import com.upedge.ums.modules.account.response.RechargeLogListResponse;
import com.upedge.ums.modules.account.response.RechargeLogUpdateResponse;
import javax.validation.Valid;

/**
 * 充值记录表— 客户消费时先查这张表扣款（余额+返点）
 *
 * @author gx
 */
@RestController
@RequestMapping("/rechargeLog")
public class RechargeLogController {
    @Autowired
    private RechargeLogService rechargeLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:rechargelog:info:id")
    public RechargeLogInfoResponse info(@PathVariable Long id) {
        RechargeLog result = rechargeLogService.selectByPrimaryKey(id);
        RechargeLogInfoResponse res = new RechargeLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:rechargelog:list")
    public RechargeLogListResponse list(@RequestBody @Valid RechargeLogListRequest request) {
        List<RechargeLog> results = rechargeLogService.select(request);
        Long total = rechargeLogService.count(request);
        request.setTotal(total);
        RechargeLogListResponse res = new RechargeLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:rechargelog:add")
    public RechargeLogAddResponse add(@RequestBody @Valid RechargeLogAddRequest request) {
        RechargeLog entity=request.toRechargeLog();
        rechargeLogService.insertSelective(entity);
        RechargeLogAddResponse res = new RechargeLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargelog:del:id")
    public RechargeLogDelResponse del(@PathVariable Long id) {
        rechargeLogService.deleteByPrimaryKey(id);
        RechargeLogDelResponse res = new RechargeLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargelog:update")
    public RechargeLogUpdateResponse update(@PathVariable Long id,@RequestBody @Valid RechargeLogUpdateRequest request) {
        RechargeLog entity=request.toRechargeLog(id);
        rechargeLogService.updateByPrimaryKeySelective(entity);
        RechargeLogUpdateResponse res = new RechargeLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
