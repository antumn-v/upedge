package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.AccountLog;
import com.upedge.ums.modules.account.service.AccountLogService;
import org.springframework.data.redis.core.RedisTemplate;
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
@Api(tags = "账户流水")
@RestController
@RequestMapping("/accountLog")
public class AccountLogController {
    @Autowired
    private AccountLogService accountLogService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:accountlog:info:id")
    public AccountLogInfoResponse info(@PathVariable Integer id) {
        AccountLog result = accountLogService.selectByPrimaryKey(id);
        AccountLogInfoResponse res = new AccountLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("账户流水列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:accountlog:list")
    public AccountLogListResponse list(@RequestBody @Valid AccountLogListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN){
            if (request.getT() == null){
                request.setT(new AccountLog());
            }
            request.getT().setCustomerId(session.getCustomerId());
        }
        List<AccountLog> results = accountLogService.select(request);
        Long total = accountLogService.count(request);
        request.setTotal(total);
        AccountLogListResponse res = new AccountLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }




}
