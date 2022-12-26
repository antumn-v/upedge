package com.upedge.oms.modules.pack.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.oms.modules.pack.entity.OrderPackageImportLog;
import com.upedge.oms.modules.pack.service.OrderPackageImportLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.oms.modules.pack.request.OrderPackageImportLogAddRequest;
import com.upedge.oms.modules.pack.request.OrderPackageImportLogListRequest;
import com.upedge.oms.modules.pack.request.OrderPackageImportLogUpdateRequest;

import com.upedge.oms.modules.pack.response.OrderPackageImportLogAddResponse;
import com.upedge.oms.modules.pack.response.OrderPackageImportLogDelResponse;
import com.upedge.oms.modules.pack.response.OrderPackageImportLogInfoResponse;
import com.upedge.oms.modules.pack.response.OrderPackageImportLogListResponse;
import com.upedge.oms.modules.pack.response.OrderPackageImportLogUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/orderPackageImportLog")
public class OrderPackageImportLogController {
    @Autowired
    private OrderPackageImportLogService orderPackageImportLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pack:orderpackageimportlog:info:id")
    public OrderPackageImportLogInfoResponse info(@PathVariable Integer id) {
        OrderPackageImportLog result = orderPackageImportLogService.selectByPrimaryKey(id);
        OrderPackageImportLogInfoResponse res = new OrderPackageImportLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackageimportlog:list")
    public OrderPackageImportLogListResponse list(@RequestBody @Valid OrderPackageImportLogListRequest request) {
        List<OrderPackageImportLog> results = orderPackageImportLogService.select(request);
        Long total = orderPackageImportLogService.count(request);
        request.setTotal(total);
        OrderPackageImportLogListResponse res = new OrderPackageImportLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackageimportlog:add")
    public OrderPackageImportLogAddResponse add(@RequestBody @Valid OrderPackageImportLogAddRequest request) {
        OrderPackageImportLog entity=request.toOrderPackageImportLog();
        orderPackageImportLogService.insertSelective(entity);
        OrderPackageImportLogAddResponse res = new OrderPackageImportLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackageimportlog:del:id")
    public OrderPackageImportLogDelResponse del(@PathVariable Integer id) {
        orderPackageImportLogService.deleteByPrimaryKey(id);
        OrderPackageImportLogDelResponse res = new OrderPackageImportLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackageimportlog:update")
    public OrderPackageImportLogUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid OrderPackageImportLogUpdateRequest request) {
        OrderPackageImportLog entity=request.toOrderPackageImportLog(id);
        orderPackageImportLogService.updateByPrimaryKeySelective(entity);
        OrderPackageImportLogUpdateResponse res = new OrderPackageImportLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
