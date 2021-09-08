package com.upedge.ums.modules.application.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.PermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.application.entity.TPermission;
import com.upedge.ums.modules.application.service.TPermissionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.application.request.TPermissionAddRequest;
import com.upedge.ums.modules.application.request.TPermissionListRequest;
import com.upedge.ums.modules.application.request.TPermissionUpdateRequest;

import com.upedge.ums.modules.application.response.TPermissionAddResponse;
import com.upedge.ums.modules.application.response.TPermissionDelResponse;
import com.upedge.ums.modules.application.response.TPermissionInfoResponse;
import com.upedge.ums.modules.application.response.TPermissionListResponse;
import com.upedge.ums.modules.application.response.TPermissionUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "权限管理")
@RestController
@RequestMapping("/permission")
public class TPermissionController {
    @Autowired
    private TPermissionService tPermissionService;


    @GetMapping("/tree")
    public BaseResponse permissionTree() throws CustomerException {
        List<PermissionVo> permissionVos = tPermissionService.treePermission();
        return BaseResponse.success(permissionVos);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "application:tpermission:info:id")
    public TPermissionInfoResponse info(@PathVariable Long id) {
        TPermission result = tPermissionService.selectByPrimaryKey(id);
        TPermissionInfoResponse res = new TPermissionInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "application:tpermission:list")
    public TPermissionListResponse list(@RequestBody @Valid TPermissionListRequest request) {
        request.setPageSize(-1);
        List<TPermission> results = tPermissionService.select(request);
        Long total = tPermissionService.count(request);
        request.setTotal(total);
        TPermissionListResponse res = new TPermissionListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "application:tpermission:add")
    public TPermissionAddResponse add(@RequestBody @Valid TPermissionAddRequest request) {
        TPermission entity=request.toTPermission();
        tPermissionService.insertSelective(entity);
        TPermissionAddResponse res = new TPermissionAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:tpermission:del:id")
    public TPermissionDelResponse del(@PathVariable Long id) {
        tPermissionService.deleteByPrimaryKey(id);
        TPermissionDelResponse res = new TPermissionDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "application:tpermission:update")
    public TPermissionUpdateResponse update(@PathVariable Long id,@RequestBody @Valid TPermissionUpdateRequest request) {
        TPermission entity=request.toTPermission(id);
        tPermissionService.updateByPrimaryKeySelective(entity);
        TPermissionUpdateResponse res = new TPermissionUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
