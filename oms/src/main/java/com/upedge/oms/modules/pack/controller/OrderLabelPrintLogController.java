package com.upedge.oms.modules.pack.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.request.OrderLabelPrintLogAddRequest;
import com.upedge.oms.modules.pack.request.OrderLabelPrintLogListRequest;
import com.upedge.oms.modules.pack.request.OrderLabelPrintLogUpdateRequest;
import com.upedge.oms.modules.pack.response.*;
import com.upedge.oms.modules.pack.service.OrderLabelPrintLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "面单打印记录")
@RestController
@RequestMapping("/orderLabelPrintLog")
public class OrderLabelPrintLogController {
    @Autowired
    private OrderLabelPrintLogService orderLabelPrintLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pack:orderlabelprintlog:info:id")
    public OrderLabelPrintLogInfoResponse info(@PathVariable Long id) {
        OrderLabelPrintLog result = orderLabelPrintLogService.selectByPrimaryKey(id);
        OrderLabelPrintLogInfoResponse res = new OrderLabelPrintLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:orderlabelprintlog:list")
    public OrderLabelPrintLogListResponse list(@RequestBody @Valid OrderLabelPrintLogListRequest request) {
        List<OrderLabelPrintLog> results = orderLabelPrintLogService.select(request);
        Long total = orderLabelPrintLogService.count(request);
        request.setTotal(total);
        OrderLabelPrintLogListResponse res = new OrderLabelPrintLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pack:orderlabelprintlog:add")
    public OrderLabelPrintLogAddResponse add(@RequestBody @Valid OrderLabelPrintLogAddRequest request) {
        OrderLabelPrintLog entity=request.toOrderLabelPrintLog();
        orderLabelPrintLogService.insertSelective(entity);
        OrderLabelPrintLogAddResponse res = new OrderLabelPrintLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderlabelprintlog:del:id")
    public OrderLabelPrintLogDelResponse del(@PathVariable Long id) {
        orderLabelPrintLogService.deleteByPrimaryKey(id);
        OrderLabelPrintLogDelResponse res = new OrderLabelPrintLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderlabelprintlog:update")
    public OrderLabelPrintLogUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OrderLabelPrintLogUpdateRequest request) {
        OrderLabelPrintLog entity=request.toOrderLabelPrintLog(id);
        orderLabelPrintLogService.updateByPrimaryKeySelective(entity);
        OrderLabelPrintLogUpdateResponse res = new OrderLabelPrintLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
