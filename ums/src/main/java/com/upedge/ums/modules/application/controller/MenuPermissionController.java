package com.upedge.ums.modules.application.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.application.entity.MenuPermission;
import com.upedge.ums.modules.application.service.MenuPermissionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.application.request.MenuPermissionAddRequest;
import com.upedge.ums.modules.application.request.MenuPermissionListRequest;
import com.upedge.ums.modules.application.request.MenuPermissionUpdateRequest;

import com.upedge.ums.modules.application.response.MenuPermissionAddResponse;
import com.upedge.ums.modules.application.response.MenuPermissionDelResponse;
import com.upedge.ums.modules.application.response.MenuPermissionInfoResponse;
import com.upedge.ums.modules.application.response.MenuPermissionListResponse;
import com.upedge.ums.modules.application.response.MenuPermissionUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/menuPermission")
public class MenuPermissionController {
    @Autowired
    private MenuPermissionService menuPermissionService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "application:menupermission:info:id")
    public MenuPermissionInfoResponse info(@PathVariable Long id) {
        MenuPermission result = menuPermissionService.selectByPrimaryKey(id);
        MenuPermissionInfoResponse res = new MenuPermissionInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "application:menupermission:list")
    public MenuPermissionListResponse list(@RequestBody @Valid MenuPermissionListRequest request) {
        List<MenuPermission> results = menuPermissionService.select(request);
        Long total = menuPermissionService.count(request);
        request.setTotal(total);
        MenuPermissionListResponse res = new MenuPermissionListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "application:menupermission:add")
    public MenuPermissionAddResponse add(@RequestBody @Valid MenuPermissionAddRequest request) {
        MenuPermission entity=request.toMenuPermission();
        menuPermissionService.insertSelective(entity);
        MenuPermissionAddResponse res = new MenuPermissionAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:menupermission:del:id")
    public MenuPermissionDelResponse del(@PathVariable Long id) {
        menuPermissionService.deleteByPrimaryKey(id);
        MenuPermissionDelResponse res = new MenuPermissionDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:menupermission:update")
    public MenuPermissionUpdateResponse update(@PathVariable Long id,@RequestBody @Valid MenuPermissionUpdateRequest request) {
        MenuPermission entity=request.toMenuPermission(id);
        menuPermissionService.updateByPrimaryKeySelective(entity);
        MenuPermissionUpdateResponse res = new MenuPermissionUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
