package com.upedge.ums.modules.manager.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.request.ManagerAddCommissionRequest;
import com.upedge.ums.modules.manager.entity.ManagerCommissionRecord;
import com.upedge.ums.modules.manager.request.ManagerCommissionRecordListRequest;
import com.upedge.ums.modules.manager.response.ManagerCommissionRecordListResponse;
import com.upedge.ums.modules.manager.service.ManagerCommissionRecordService;
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
@RequestMapping("/managerCommissionRecord")
public class ManagerCommissionRecordController {
    @Autowired
    private ManagerCommissionRecordService managerCommissionRecordService;


    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:ManagerCommissionRecord:list")
    public ManagerCommissionRecordListResponse list(@RequestBody @Valid ManagerCommissionRecordListRequest request) {
        List<ManagerCommissionRecord> results = managerCommissionRecordService.select(request);
        Long total = managerCommissionRecordService.count(request);
        request.setTotal(total);
        ManagerCommissionRecordListResponse res = new ManagerCommissionRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public BaseResponse add(@RequestBody @Valid ManagerAddCommissionRequest request) {
        managerCommissionRecordService.addCommissionRecord(request);
        return BaseResponse.success();
    }



}
