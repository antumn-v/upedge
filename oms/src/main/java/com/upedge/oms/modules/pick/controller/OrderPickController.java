package com.upedge.oms.modules.pick.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickAddRequest;
import com.upedge.oms.modules.pick.request.OrderPickListRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.OrderPickUpdateRequest;
import com.upedge.oms.modules.pick.response.*;
import com.upedge.oms.modules.pick.service.OrderPickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 订单拣货
 *
 * @author gx
 */
@RestController
@RequestMapping("/orderPick")
public class OrderPickController {
    @Autowired
    private OrderPickService orderPickService;


    @PostMapping("/previewList")
    public BaseResponse previewList(@RequestBody OrderPickPreviewListRequest request){
        return orderPickService.previewList(request);
    }


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pick:orderpick:info:id")
    public OrderPickInfoResponse info(@PathVariable Long id) {
        OrderPick result = orderPickService.selectByPrimaryKey(id);
        OrderPickInfoResponse res = new OrderPickInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pick:orderpick:list")
    public OrderPickListResponse list(@RequestBody @Valid OrderPickListRequest request) {
        List<OrderPick> results = orderPickService.select(request);
        Long total = orderPickService.count(request);
        request.setTotal(total);
        OrderPickListResponse res = new OrderPickListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pick:orderpick:add")
    public OrderPickAddResponse add(@RequestBody @Valid OrderPickAddRequest request) {
        OrderPick entity=request.toOrderPick();
        orderPickService.insertSelective(entity);
        OrderPickAddResponse res = new OrderPickAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pick:orderpick:del:id")
    public OrderPickDelResponse del(@PathVariable Long id) {
        orderPickService.deleteByPrimaryKey(id);
        OrderPickDelResponse res = new OrderPickDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pick:orderpick:update")
    public OrderPickUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OrderPickUpdateRequest request) {
        OrderPick entity=request.toOrderPick(id);
        orderPickService.updateByPrimaryKeySelective(entity);
        OrderPickUpdateResponse res = new OrderPickUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
