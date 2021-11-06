package com.upedge.oms.modules.order.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.order.entity.StoreOrderRefund;
import com.upedge.oms.modules.order.request.StoreOrderRefundAddRequest;
import com.upedge.oms.modules.order.request.StoreOrderRefundListRequest;
import com.upedge.oms.modules.order.request.StoreOrderRefundUpdateRequest;
import com.upedge.oms.modules.order.response.*;
import com.upedge.oms.modules.order.service.StoreOrderRefundService;
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
@RequestMapping("/storeOrderRefund")
public class StoreOrderRefundController {
    @Autowired
    private StoreOrderRefundService storeOrderRefundService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:storeorderrefund:info:id")
    public StoreOrderRefundInfoResponse info(@PathVariable Long id) {
        StoreOrderRefund result = storeOrderRefundService.selectByPrimaryKey(id);
        StoreOrderRefundInfoResponse res = new StoreOrderRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefund:list")
    public StoreOrderRefundListResponse list(@RequestBody @Valid StoreOrderRefundListRequest request) {
        List<StoreOrderRefund> results = storeOrderRefundService.select(request);
        Long total = storeOrderRefundService.count(request);
        request.setTotal(total);
        StoreOrderRefundListResponse res = new StoreOrderRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefund:add")
    public StoreOrderRefundAddResponse add(@RequestBody @Valid StoreOrderRefundAddRequest request) {
        StoreOrderRefund entity=request.toStoreOrderRefund();
        storeOrderRefundService.insertSelective(entity);
        StoreOrderRefundAddResponse res = new StoreOrderRefundAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefund:del:id")
    public StoreOrderRefundDelResponse del(@PathVariable Long id) {
        storeOrderRefundService.deleteByPrimaryKey(id);
        StoreOrderRefundDelResponse res = new StoreOrderRefundDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefund:update")
    public StoreOrderRefundUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StoreOrderRefundUpdateRequest request) {
        StoreOrderRefund entity=request.toStoreOrderRefund(id);
        storeOrderRefundService.updateByPrimaryKeySelective(entity);
        StoreOrderRefundUpdateResponse res = new StoreOrderRefundUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
