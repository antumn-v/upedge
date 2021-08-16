package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.service.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.account.request.AccountAddRequest;
import com.upedge.ums.modules.account.request.AccountListRequest;
import com.upedge.ums.modules.account.request.AccountUpdateRequest;

import com.upedge.ums.modules.account.response.AccountAddResponse;
import com.upedge.ums.modules.account.response.AccountDelResponse;
import com.upedge.ums.modules.account.response.AccountInfoResponse;
import com.upedge.ums.modules.account.response.AccountListResponse;
import com.upedge.ums.modules.account.response.AccountUpdateResponse;
import javax.validation.Valid;

/**
 * 账户表
 *
 * @author gx
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:account:info:id")
    public AccountInfoResponse info(@PathVariable Long id) {
        Account result = accountService.selectByPrimaryKey(id);
        AccountInfoResponse res = new AccountInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:account:list")
    public AccountListResponse list(@RequestBody @Valid AccountListRequest request) {
        List<Account> results = accountService.select(request);
        Long total = accountService.count(request);
        request.setTotal(total);
        AccountListResponse res = new AccountListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:account:add")
    public AccountAddResponse add(@RequestBody @Valid AccountAddRequest request) {
        Account entity=request.toAccount();
        accountService.insertSelective(entity);
        AccountAddResponse res = new AccountAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:account:del:id")
    public AccountDelResponse del(@PathVariable Long id) {
        accountService.deleteByPrimaryKey(id);
        AccountDelResponse res = new AccountDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:account:update")
    public AccountUpdateResponse update(@PathVariable Long id,@RequestBody @Valid AccountUpdateRequest request) {
        Account entity=request.toAccount(id);
        accountService.updateByPrimaryKeySelective(entity);
        AccountUpdateResponse res = new AccountUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
