package com.upedge.ums.modules.manager.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionListRequest;
import com.upedge.ums.modules.manager.response.ManagerMonthCommissionListResponse;
import com.upedge.ums.modules.manager.service.ManagerMonthCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/managerMonthCommission")
public class ManagerMonthCommissionController {
    @Autowired
    private ManagerMonthCommissionService managerMonthCommissionService;


    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:managermonthcommission:list")
    public ManagerMonthCommissionListResponse list(@RequestBody @Valid ManagerMonthCommissionListRequest request) {
        List<ManagerMonthCommission> results = managerMonthCommissionService.select(request);
        Long total = managerMonthCommissionService.count(request);
        request.setTotal(total);
        ManagerMonthCommissionListResponse res = new ManagerMonthCommissionListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }




}
