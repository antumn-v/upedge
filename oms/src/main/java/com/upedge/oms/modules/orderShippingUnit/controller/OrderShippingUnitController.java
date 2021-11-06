package com.upedge.oms.modules.orderShippingUnit.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.request.OrderShippingUnitAddRequest;
import com.upedge.oms.modules.orderShippingUnit.request.OrderShippingUnitListRequest;
import com.upedge.oms.modules.orderShippingUnit.request.OrderShippingUnitUpdateRequest;
import com.upedge.oms.modules.orderShippingUnit.response.*;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 运输单元表
 *
 * @author xwCui
 */
@RestController
@RequestMapping("/orderShippingUnit")
public class OrderShippingUnitController {
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "orderShippingUnit:ordershippingunit:info:id")
    public OrderShippingUnitInfoResponse info(@PathVariable Long id) {
        OrderShippingUnit result = orderShippingUnitService.selectByPrimaryKey(id);
        OrderShippingUnitInfoResponse res = new OrderShippingUnitInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "orderShippingUnit:ordershippingunit:list")
    public OrderShippingUnitListResponse list(@RequestBody @Valid OrderShippingUnitListRequest request) {
        List<OrderShippingUnit> results = orderShippingUnitService.select(request);
        Long total = orderShippingUnitService.count(request);
        request.setTotal(total);
        OrderShippingUnitListResponse res = new OrderShippingUnitListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "orderShippingUnit:ordershippingunit:add")
    public OrderShippingUnitAddResponse add(@RequestBody @Valid OrderShippingUnitAddRequest request) {
        OrderShippingUnit entity=request.toOrderShippingUnit();
        orderShippingUnitService.insertSelective(entity);
        OrderShippingUnitAddResponse res = new OrderShippingUnitAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "orderShippingUnit:ordershippingunit:del:id")
    public OrderShippingUnitDelResponse del(@PathVariable Long id) {
        orderShippingUnitService.deleteByPrimaryKey(id);
        OrderShippingUnitDelResponse res = new OrderShippingUnitDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "orderShippingUnit:ordershippingunit:update")
    public OrderShippingUnitUpdateResponse update(@PathVariable Long id, @RequestBody @Valid OrderShippingUnitUpdateRequest request) {
        OrderShippingUnit entity=request.toOrderShippingUnit(id);
        orderShippingUnitService.updateByPrimaryKeySelective(entity);
        OrderShippingUnitUpdateResponse res = new OrderShippingUnitUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
