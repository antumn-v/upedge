package com.upedge.ums.modules.account.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.entity.AccountWithdrawLog;
import com.upedge.ums.modules.account.request.AccountBalanceWithdrawRequest;
import com.upedge.ums.modules.account.request.AccountWithdrawLogListRequest;
import com.upedge.ums.modules.account.response.AccountWithdrawLogListResponse;
import com.upedge.ums.modules.account.service.AccountWithdrawLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "账户提现")
@RestController
@RequestMapping("/accountWithdraw")
public class AccountWithdrawLogController {
    @Autowired
    private AccountWithdrawLogService accountWithdrawLogService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("账单余额提现")
    @PostMapping("/request")
    public BaseResponse accountWithdrawRequest(@RequestBody@Valid AccountBalanceWithdrawRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return accountWithdrawLogService.accountWithdrawRequest(request,session);
    }


    @ApiOperation("提现通过")
    @PostMapping("/confirm/{id}")
    public BaseResponse confirmWithdrawRequest(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return accountWithdrawLogService.withdrawConfirm(id,session);
    }

    @ApiOperation("提现通过")
    @PostMapping("/reject/{id}")
    public BaseResponse reject(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return accountWithdrawLogService.reject(id,session);
    }

    @ApiOperation("提现记录")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:accountwithdrawlog:list")
    public AccountWithdrawLogListResponse list(@RequestBody @Valid AccountWithdrawLogListRequest request) {
        List<AccountWithdrawLog> results = accountWithdrawLogService.select(request);
        Long total = accountWithdrawLogService.count(request);
        request.setTotal(total);
        AccountWithdrawLogListResponse res = new AccountWithdrawLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



}
