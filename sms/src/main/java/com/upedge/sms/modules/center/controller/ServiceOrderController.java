package com.upedge.sms.modules.center.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.sms.modules.center.request.ServiceOrderAddRequest;
import com.upedge.sms.modules.center.request.ServiceOrderListRequest;
import com.upedge.sms.modules.center.request.ServiceOrderUpdateRequest;

import com.upedge.sms.modules.center.response.ServiceOrderAddResponse;
import com.upedge.sms.modules.center.response.ServiceOrderDelResponse;
import com.upedge.sms.modules.center.response.ServiceOrderInfoResponse;
import com.upedge.sms.modules.center.response.ServiceOrderListResponse;
import com.upedge.sms.modules.center.response.ServiceOrderUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/serviceOrder")
public class ServiceOrderController {
    @Autowired
    private ServiceOrderService serviceOrderService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "center:serviceorder:info:id")
    public ServiceOrderInfoResponse info(@PathVariable Long id) {
        ServiceOrder result = serviceOrderService.selectByPrimaryKey(id);
        ServiceOrderInfoResponse res = new ServiceOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "center:serviceorder:list")
    public ServiceOrderListResponse list(@RequestBody @Valid ServiceOrderListRequest request) {
        List<ServiceOrder> results = serviceOrderService.select(request);
        Long total = serviceOrderService.count(request);
        request.setTotal(total);
        ServiceOrderListResponse res = new ServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "center:serviceorder:add")
    public ServiceOrderAddResponse add(@RequestBody @Valid ServiceOrderAddRequest request) {
        ServiceOrder entity=request.toServiceOrder();
        serviceOrderService.insertSelective(entity);
        ServiceOrderAddResponse res = new ServiceOrderAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "center:serviceorder:del:id")
    public ServiceOrderDelResponse del(@PathVariable Long id) {
        serviceOrderService.deleteByPrimaryKey(id);
        ServiceOrderDelResponse res = new ServiceOrderDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "center:serviceorder:update")
    public ServiceOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ServiceOrderUpdateRequest request) {
        ServiceOrder entity=request.toServiceOrder(id);
        serviceOrderService.updateByPrimaryKeySelective(entity);
        ServiceOrderUpdateResponse res = new ServiceOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
