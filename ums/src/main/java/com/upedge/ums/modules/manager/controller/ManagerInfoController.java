package com.upedge.ums.modules.manager.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;
import com.upedge.ums.modules.manager.request.ManagerInfoListRequest;
import com.upedge.ums.modules.manager.request.ManagerInfoUpdateRequest;
import com.upedge.ums.modules.manager.response.*;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/managerInfo")
public class ManagerInfoController {
    @Autowired
    private ManagerInfoService managerInfoService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "manager:managerinfo:info:id")
    public ManagerInfoInfoResponse info(@PathVariable Long id) {
        ManagerInfo result = managerInfoService.selectByPrimaryKey(id);
        ManagerInfoInfoResponse res = new ManagerInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:list")
    public ManagerInfoListResponse list(@RequestBody @Valid ManagerInfoListRequest request) {
        List<ManagerInfo> results = managerInfoService.select(request);
        Long total = managerInfoService.count(request);
        request.setTotal(total);
        ManagerInfoListResponse res = new ManagerInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:add")
    public ManagerInfoAddResponse add(@RequestBody @Valid ManagerInfoAddRequest request) {
        ManagerInfo entity=request.toManagerInfo();
        managerInfoService.insertSelective(entity);
        ManagerInfoAddResponse res = new ManagerInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:del:id")
    public ManagerInfoDelResponse del(@PathVariable Long id) {
        managerInfoService.deleteByPrimaryKey(id);
        ManagerInfoDelResponse res = new ManagerInfoDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "manager:managerinfo:update")
    public ManagerInfoUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ManagerInfoUpdateRequest request) {
        ManagerInfo entity=request.toManagerInfo(id);
        managerInfoService.updateByPrimaryKeySelective(entity);
        ManagerInfoUpdateResponse res = new ManagerInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
