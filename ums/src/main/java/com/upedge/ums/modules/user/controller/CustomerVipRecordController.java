package com.upedge.ums.modules.user.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import com.upedge.ums.modules.user.request.CustomerVipRecordAddRequest;
import com.upedge.ums.modules.user.request.CustomerVipRecordListRequest;
import com.upedge.ums.modules.user.response.CustomerVipRecordAddResponse;
import com.upedge.ums.modules.user.response.CustomerVipRecordListResponse;
import com.upedge.ums.modules.user.service.CustomerVipRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "用户vip管理")
@RestController
@RequestMapping("/customerVip")
public class CustomerVipRecordController {
    @Autowired
    private CustomerVipRecordService customerVipRecordService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("vip授权撤销记录")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:list")
    public CustomerVipRecordListResponse list(@RequestBody @Valid CustomerVipRecordListRequest request) {
        List<CustomerVipRecord> results = customerVipRecordService.select(request);
        Long total = customerVipRecordService.count(request);
        request.setTotal(total);
        CustomerVipRecordListResponse res = new CustomerVipRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("授权撤销用户vip")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "user:customerviprecord:add")
    public CustomerVipRecordAddResponse add(@RequestBody @Valid CustomerVipRecordAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        CustomerVipRecord entity=request.toCustomerVipRecord(session);
        customerVipRecordService.insertSelective(entity);
        CustomerVipRecordAddResponse res = new CustomerVipRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }



}
