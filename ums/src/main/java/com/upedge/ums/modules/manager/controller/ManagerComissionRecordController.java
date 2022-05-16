package com.upedge.ums.modules.manager.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.manager.entity.ManagerComissionRecord;
import com.upedge.ums.modules.manager.service.ManagerComissionRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.manager.request.ManagerComissionRecordAddRequest;
import com.upedge.ums.modules.manager.request.ManagerComissionRecordListRequest;
import com.upedge.ums.modules.manager.request.ManagerComissionRecordUpdateRequest;

import com.upedge.ums.modules.manager.response.ManagerComissionRecordAddResponse;
import com.upedge.ums.modules.manager.response.ManagerComissionRecordDelResponse;
import com.upedge.ums.modules.manager.response.ManagerComissionRecordInfoResponse;
import com.upedge.ums.modules.manager.response.ManagerComissionRecordListResponse;
import com.upedge.ums.modules.manager.response.ManagerComissionRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/managerComissionRecord")
public class ManagerComissionRecordController {
    @Autowired
    private ManagerComissionRecordService managerComissionRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "manager:managercomissionrecord:info:id")
    public ManagerComissionRecordInfoResponse info(@PathVariable Long id) {
        ManagerComissionRecord result = managerComissionRecordService.selectByPrimaryKey(id);
        ManagerComissionRecordInfoResponse res = new ManagerComissionRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:managercomissionrecord:list")
    public ManagerComissionRecordListResponse list(@RequestBody @Valid ManagerComissionRecordListRequest request) {
        List<ManagerComissionRecord> results = managerComissionRecordService.select(request);
        Long total = managerComissionRecordService.count(request);
        request.setTotal(total);
        ManagerComissionRecordListResponse res = new ManagerComissionRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "manager:managercomissionrecord:add")
    public ManagerComissionRecordAddResponse add(@RequestBody @Valid ManagerComissionRecordAddRequest request) {
        ManagerComissionRecord entity=request.toManagerComissionRecord();
        managerComissionRecordService.insertSelective(entity);
        ManagerComissionRecordAddResponse res = new ManagerComissionRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managercomissionrecord:del:id")
    public ManagerComissionRecordDelResponse del(@PathVariable Long id) {
        managerComissionRecordService.deleteByPrimaryKey(id);
        ManagerComissionRecordDelResponse res = new ManagerComissionRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managercomissionrecord:update")
    public ManagerComissionRecordUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ManagerComissionRecordUpdateRequest request) {
        ManagerComissionRecord entity=request.toManagerComissionRecord(id);
        managerComissionRecordService.updateByPrimaryKeySelective(entity);
        ManagerComissionRecordUpdateResponse res = new ManagerComissionRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
