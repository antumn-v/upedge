package com.upedge.oms.modules.order.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.order.entity.OrderRefundItem;
import com.upedge.oms.modules.order.request.OrderRefundItemAddRequest;
import com.upedge.oms.modules.order.request.OrderRefundItemListRequest;
import com.upedge.oms.modules.order.request.OrderRefundItemUpdateRequest;
import com.upedge.oms.modules.order.response.*;
import com.upedge.oms.modules.order.service.OrderRefundItemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/orderRefundItem")
public class OrderRefundItemController {
    @Autowired
    private OrderRefundItemService orderRefundItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:orderrefunditem:info:id")
    public OrderRefundItemInfoResponse info(@PathVariable Integer id) {
        OrderRefundItem result = orderRefundItemService.selectByPrimaryKey(id);
        OrderRefundItemInfoResponse res = new OrderRefundItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefunditem:list")
    public OrderRefundItemListResponse list(@RequestBody @Valid OrderRefundItemListRequest request) {
        List<OrderRefundItem> results = orderRefundItemService.select(request);
        Long total = orderRefundItemService.count(request);
        request.setTotal(total);
        OrderRefundItemListResponse res = new OrderRefundItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefunditem:add")
    public OrderRefundItemAddResponse add(@RequestBody @Valid OrderRefundItemAddRequest request) {
        OrderRefundItem entity=request.toOrderRefundItem();
        orderRefundItemService.insertSelective(entity);
        OrderRefundItemAddResponse res = new OrderRefundItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefunditem:del:id")
    public OrderRefundItemDelResponse del(@PathVariable Integer id) {
        orderRefundItemService.deleteByPrimaryKey(id);
        OrderRefundItemDelResponse res = new OrderRefundItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefunditem:update")
    public OrderRefundItemUpdateResponse update(@PathVariable Integer id, @RequestBody @Valid OrderRefundItemUpdateRequest request) {
        OrderRefundItem entity=request.toOrderRefundItem(id);
        orderRefundItemService.updateByPrimaryKeySelective(entity);
        OrderRefundItemUpdateResponse res = new OrderRefundItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
