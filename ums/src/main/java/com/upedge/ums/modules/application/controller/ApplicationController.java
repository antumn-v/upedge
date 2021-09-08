package com.upedge.ums.modules.application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.application.vo.ApplicationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.application.entity.Application;
import com.upedge.ums.modules.application.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.application.request.ApplicationAddRequest;
import com.upedge.ums.modules.application.request.ApplicationListRequest;
import com.upedge.ums.modules.application.request.ApplicationUpdateRequest;

import com.upedge.ums.modules.application.response.ApplicationAddResponse;
import com.upedge.ums.modules.application.response.ApplicationDelResponse;
import com.upedge.ums.modules.application.response.ApplicationInfoResponse;
import com.upedge.ums.modules.application.response.ApplicationListResponse;
import com.upedge.ums.modules.application.response.ApplicationUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "应用管理")
@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @ApiOperation("所有应用")
    @GetMapping("/all")
    public BaseResponse allApplications(){
        Page<Application> applicationPage = new Page<>();
        applicationPage.setPageSize(-1);
        List<Application> applications = applicationService.select(applicationPage);
        List<ApplicationVo> applicationVoList=new ArrayList<>();
        applications.forEach(application -> {
            ApplicationVo applicationVo=new ApplicationVo();
            BeanUtils.copyProperties(application,applicationVo);
            if(!StringUtils.isBlank(application.getMenuGroup())){
                String [] groupArr=application.getMenuGroup().split(",");
                applicationVo.setMenuGroupList(Arrays.asList(groupArr));
                applicationVoList.add(applicationVo);
            }
        });
        return BaseResponse.success(applicationVoList);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "application:application:info:id")
    public ApplicationInfoResponse info(@PathVariable Long id) {
        Application result = applicationService.selectByPrimaryKey(id);
        ApplicationInfoResponse res = new ApplicationInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "application:application:list")
    public ApplicationListResponse list(@RequestBody @Valid ApplicationListRequest request) {
        List<Application> results = applicationService.select(request);
        Long total = applicationService.count(request);
        request.setTotal(total);
        ApplicationListResponse res = new ApplicationListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "application:application:add")
    public ApplicationAddResponse add(@RequestBody @Valid ApplicationAddRequest request) {
        Application entity=request.toApplication();
        applicationService.insertSelective(entity);
        ApplicationAddResponse res = new ApplicationAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:application:del:id")
    public ApplicationDelResponse del(@PathVariable Long id) {
        applicationService.deleteByPrimaryKey(id);
        ApplicationDelResponse res = new ApplicationDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:application:update")
    public ApplicationUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ApplicationUpdateRequest request) {
        Application entity=request.toApplication(id);
        applicationService.updateByPrimaryKeySelective(entity);
        ApplicationUpdateResponse res = new ApplicationUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
