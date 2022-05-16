package com.upedge.ums.modules.manager.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import com.upedge.ums.modules.manager.service.ManagerMonthCommissionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionAddRequest;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionListRequest;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionUpdateRequest;

import com.upedge.ums.modules.manager.response.ManagerMonthCommissionAddResponse;
import com.upedge.ums.modules.manager.response.ManagerMonthCommissionDelResponse;
import com.upedge.ums.modules.manager.response.ManagerMonthCommissionInfoResponse;
import com.upedge.ums.modules.manager.response.ManagerMonthCommissionListResponse;
import com.upedge.ums.modules.manager.response.ManagerMonthCommissionUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/managerMonthCommission")
public class ManagerMonthCommissionController {
    @Autowired
    private ManagerMonthCommissionService managerMonthCommissionService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "manager:managermonthcommission:info:id")
    public ManagerMonthCommissionInfoResponse info(@PathVariable Long id) {
        ManagerMonthCommission result = managerMonthCommissionService.selectByPrimaryKey(id);
        ManagerMonthCommissionInfoResponse res = new ManagerMonthCommissionInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:managermonthcommission:list")
    public ManagerMonthCommissionListResponse list(@RequestBody @Valid ManagerMonthCommissionListRequest request) {
        List<ManagerMonthCommission> results = managerMonthCommissionService.select(request);
        Long total = managerMonthCommissionService.count(request);
        request.setTotal(total);
        ManagerMonthCommissionListResponse res = new ManagerMonthCommissionListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "manager:managermonthcommission:add")
    public ManagerMonthCommissionAddResponse add(@RequestBody @Valid ManagerMonthCommissionAddRequest request) {
        ManagerMonthCommission entity=request.toManagerMonthCommission();
        managerMonthCommissionService.insertSelective(entity);
        ManagerMonthCommissionAddResponse res = new ManagerMonthCommissionAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managermonthcommission:del:id")
    public ManagerMonthCommissionDelResponse del(@PathVariable Long id) {
        managerMonthCommissionService.deleteByPrimaryKey(id);
        ManagerMonthCommissionDelResponse res = new ManagerMonthCommissionDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managermonthcommission:update")
    public ManagerMonthCommissionUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ManagerMonthCommissionUpdateRequest request) {
        ManagerMonthCommission entity=request.toManagerMonthCommission(id);
        managerMonthCommissionService.updateByPrimaryKeySelective(entity);
        ManagerMonthCommissionUpdateResponse res = new ManagerMonthCommissionUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
