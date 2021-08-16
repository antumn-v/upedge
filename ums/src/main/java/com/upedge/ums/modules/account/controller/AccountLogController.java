package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.service.AccountLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.AccountLogAddRequest;
import com.upedge.ums.modules.account.request.AccountLogListRequest;
import com.upedge.ums.modules.account.request.AccountLogUpdateRequest;

import com.upedge.ums.modules.account.response.AccountLogAddResponse;
import com.upedge.ums.modules.account.response.AccountLogDelResponse;
import com.upedge.ums.modules.account.response.AccountLogInfoResponse;
import com.upedge.ums.modules.account.response.AccountLogListResponse;
import com.upedge.ums.modules.account.response.AccountLogUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/accountLog")
public class AccountLogController {
    @Autowired
    private AccountLogService accountLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:accountlog:info:id")
    public AccountLogInfoResponse info(@PathVariable Integer id) {
        AccountLog result = accountLogService.selectByPrimaryKey(id);
        AccountLogInfoResponse res = new AccountLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:accountlog:list")
    public AccountLogListResponse list(@RequestBody @Valid AccountLogListRequest request) {
        List<AccountLog> results = accountLogService.select(request);
        Long total = accountLogService.count(request);
        request.setTotal(total);
        AccountLogListResponse res = new AccountLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:accountlog:add")
    public AccountLogAddResponse add(@RequestBody @Valid AccountLogAddRequest request) {
        AccountLog entity=request.toAccountLog();
        accountLogService.insertSelective(entity);
        AccountLogAddResponse res = new AccountLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:accountlog:del:id")
    public AccountLogDelResponse del(@PathVariable Integer id) {
        accountLogService.deleteByPrimaryKey(id);
        AccountLogDelResponse res = new AccountLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:accountlog:update")
    public AccountLogUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid AccountLogUpdateRequest request) {
        AccountLog entity=request.toAccountLog(id);
        accountLogService.updateByPrimaryKeySelective(entity);
        AccountLogUpdateResponse res = new AccountLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
