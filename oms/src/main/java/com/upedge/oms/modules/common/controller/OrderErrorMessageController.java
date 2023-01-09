package com.upedge.oms.modules.common.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import com.upedge.oms.modules.common.request.OrderErrorMessageAddRequest;
import com.upedge.oms.modules.common.request.OrderErrorMessageListRequest;
import com.upedge.oms.modules.common.request.OrderErrorMessageUpdateRequest;
import com.upedge.oms.modules.common.response.*;
import com.upedge.oms.modules.common.service.OrderErrorMessageService;
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
@Api(tags = "订单异常信息管理")
@RestController
@RequestMapping("/orderErrorMessage")
public class OrderErrorMessageController {
    @Autowired
    private OrderErrorMessageService orderErrorMessageService;


    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "common:ordererrormessage:list")
    public OrderErrorMessageListResponse list(@RequestBody @Valid OrderErrorMessageListRequest request) {
        request.setPageSize(-1);
        List<OrderErrorMessage> results = orderErrorMessageService.select(request);
        Long total = orderErrorMessageService.count(request);
        request.setTotal(total);
        OrderErrorMessageListResponse res = new OrderErrorMessageListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "common:ordererrormessage:add")
    public OrderErrorMessageAddResponse add(@RequestBody @Valid OrderErrorMessageAddRequest request) {
        OrderErrorMessage entity=request.toOrderErrorMessage();
        orderErrorMessageService.insertSelective(entity);
        OrderErrorMessageAddResponse res = new OrderErrorMessageAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "common:ordererrormessage:del:id")
    public OrderErrorMessageDelResponse del(@PathVariable Integer id) {
        orderErrorMessageService.deleteByPrimaryKey(id);
        OrderErrorMessageDelResponse res = new OrderErrorMessageDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "common:ordererrormessage:update")
    public OrderErrorMessageUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid OrderErrorMessageUpdateRequest request) {
        OrderErrorMessage entity=request.toOrderErrorMessage(id);
        orderErrorMessageService.updateByPrimaryKeySelective(entity);
        OrderErrorMessageUpdateResponse res = new OrderErrorMessageUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
