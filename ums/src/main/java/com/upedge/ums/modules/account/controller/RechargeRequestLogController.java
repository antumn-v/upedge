package com.upedge.ums.modules.account.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.account.request.*;
import com.upedge.ums.modules.user.entity.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import com.upedge.ums.modules.account.service.RechargeRequestLogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;

import com.upedge.ums.modules.account.response.RechargeRequestLogAddResponse;
import com.upedge.ums.modules.account.response.RechargeRequestLogDelResponse;
import com.upedge.ums.modules.account.response.RechargeRequestLogInfoResponse;
import com.upedge.ums.modules.account.response.RechargeRequestLogListResponse;
import com.upedge.ums.modules.account.response.RechargeRequestLogUpdateResponse;
import javax.validation.Valid;

/**
 * 充值申请表
 *
 * @author gx
 */
@Api(tags = "/充值申请")
@RestController
@RequestMapping("/rechargeRequestLog")
public class RechargeRequestLogController {
    @Autowired
    private RechargeRequestLogService rechargeRequestLogService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("提交转账充值申请")
    @PostMapping("/transfer")
    public BaseResponse transferRechargeRequest(@RequestBody TransferRechargeRequest rechargeRequest){
        Session session = UserUtil.getSession(redisTemplate);
        return rechargeRequestLogService.transferRechargeRequest(rechargeRequest,session);
    }

    @ApiOperation("确认转账充值申请")
    @PostMapping("/confirm")
    public BaseResponse confirmTransferRecharge(@RequestBody @Valid ConfirmTransferRechargeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return rechargeRequestLogService.confirmRechargeRequest(request.getId(),session);
    }

    @ApiOperation("驳回转账充值申请")
    @PostMapping("/reject")
    public BaseResponse rejectTransferRecharge(@RequestBody @Valid RejectRechargeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return rechargeRequestLogService.rejectRechargeRequest(request,session);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "account:rechargerequestlog:info:id")
    public RechargeRequestLogInfoResponse info(@PathVariable Long id) {
        RechargeRequestLog result = rechargeRequestLogService.selectByPrimaryKey(id);
        RechargeRequestLogInfoResponse res = new RechargeRequestLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }


    @ApiOperation("充值申请列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestlog:list")
    public RechargeRequestLogListResponse list(@RequestBody @Valid RechargeRequestLogListRequest request) {
        List<RechargeRequestLog> results = rechargeRequestLogService.select(request);
        Long total = rechargeRequestLogService.count(request);
        request.setTotal(total);
        RechargeRequestLogListResponse res = new RechargeRequestLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestlog:add")
    public RechargeRequestLogAddResponse add(@RequestBody @Valid RechargeRequestLogAddRequest request) {
        RechargeRequestLog entity=request.toRechargeRequestLog();
        rechargeRequestLogService.insertSelective(entity);
        RechargeRequestLogAddResponse res = new RechargeRequestLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestlog:del:id")
    public RechargeRequestLogDelResponse del(@PathVariable Long id) {
        rechargeRequestLogService.deleteByPrimaryKey(id);
        RechargeRequestLogDelResponse res = new RechargeRequestLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "account:rechargerequestlog:update")
    public RechargeRequestLogUpdateResponse update(@PathVariable Long id,@RequestBody @Valid RechargeRequestLogUpdateRequest request) {
        RechargeRequestLog entity=request.toRechargeRequestLog(id);
        rechargeRequestLogService.updateByPrimaryKeySelective(entity);
        RechargeRequestLogUpdateResponse res = new RechargeRequestLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
