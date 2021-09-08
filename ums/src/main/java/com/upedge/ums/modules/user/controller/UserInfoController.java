package com.upedge.ums.modules.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.user.entity.UserInfo;
import com.upedge.ums.modules.user.service.UserInfoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.user.request.UserInfoAddRequest;
import com.upedge.ums.modules.user.request.UserInfoListRequest;
import com.upedge.ums.modules.user.request.UserInfoUpdateRequest;

import com.upedge.ums.modules.user.response.UserInfoAddResponse;
import com.upedge.ums.modules.user.response.UserInfoDelResponse;
import com.upedge.ums.modules.user.response.UserInfoInfoResponse;
import com.upedge.ums.modules.user.response.UserInfoListResponse;
import com.upedge.ums.modules.user.response.UserInfoUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/userInfo")
public class
UserInfoController {
    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation("用户信息详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:userinfo:info:id")
    public UserInfoInfoResponse info(@PathVariable Long id) {
        UserInfo result = userInfoService.selectByPrimaryKey(id);
        UserInfoInfoResponse res = new UserInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:userinfo:list")
    public UserInfoListResponse list(@RequestBody @Valid UserInfoListRequest request) {
        List<UserInfo> results = userInfoService.select(request);
        Long total = userInfoService.count(request);
        request.setTotal(total);
        UserInfoListResponse res = new UserInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:userinfo:add")
    public UserInfoAddResponse add(@RequestBody @Valid UserInfoAddRequest request) {
        UserInfo entity=request.toUserInfo();
        userInfoService.insertSelective(entity);
        UserInfoAddResponse res = new UserInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:userinfo:del:id")
    public UserInfoDelResponse del(@PathVariable Long id) {
        userInfoService.deleteByPrimaryKey(id);
        UserInfoDelResponse res = new UserInfoDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("修改用户信息")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:userinfo:update")
    public UserInfoUpdateResponse update(@PathVariable Long id,@RequestBody @Valid UserInfoUpdateRequest request) {
        UserInfo entity=request.toUserInfo(id);
        userInfoService.updateByPrimaryKeySelective(entity);
        UserInfoUpdateResponse res = new UserInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
