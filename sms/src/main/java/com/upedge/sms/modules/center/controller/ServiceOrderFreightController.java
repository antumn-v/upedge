package com.upedge.sms.modules.center.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.center.response.*;
import com.upedge.sms.modules.center.request.ServiceOrderFreightAddRequest;
import com.upedge.sms.modules.center.request.ServiceOrderFreightListRequest;
import com.upedge.sms.modules.center.request.ServiceOrderFreightUpdateRequest;
import com.upedge.sms.modules.center.service.ServiceOrderFreightService;
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
@RequestMapping("/ServiceOrderFreight")
public class ServiceOrderFreightController {
    @Autowired
    private ServiceOrderFreightService ServiceOrderFreightService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:ServiceOrderFreight:info:id")
    public ServiceOrderFreightInfoResponse info(@PathVariable Long id) {
        ServiceOrderFreight result = ServiceOrderFreightService.selectByPrimaryKey(id);
        ServiceOrderFreightInfoResponse res = new ServiceOrderFreightInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:ServiceOrderFreight:list")
    public ServiceOrderFreightListResponse list(@RequestBody @Valid ServiceOrderFreightListRequest request) {
        List<ServiceOrderFreight> results = ServiceOrderFreightService.select(request);
        Long total = ServiceOrderFreightService.count(request);
        request.setTotal(total);
        ServiceOrderFreightListResponse res = new ServiceOrderFreightListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:ServiceOrderFreight:add")
    public ServiceOrderFreightAddResponse add(@RequestBody @Valid ServiceOrderFreightAddRequest request) {
        ServiceOrderFreight entity=request.toServiceOrderFreight();
        ServiceOrderFreightService.insertSelective(entity);
        ServiceOrderFreightAddResponse res = new ServiceOrderFreightAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:ServiceOrderFreight:del:id")
    public ServiceOrderFreightDelResponse del(@PathVariable Long id) {
        ServiceOrderFreightService.deleteByPrimaryKey(id);
        ServiceOrderFreightDelResponse res = new ServiceOrderFreightDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:ServiceOrderFreight:update")
    public ServiceOrderFreightUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ServiceOrderFreightUpdateRequest request) {
        ServiceOrderFreight entity=request.toServiceOrderFreight(id);
        ServiceOrderFreightService.updateByPrimaryKeySelective(entity);
        ServiceOrderFreightUpdateResponse res = new ServiceOrderFreightUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
