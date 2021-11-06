package com.upedge.oms.modules.order.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;
import com.upedge.oms.modules.order.request.StoreOrderRefundItemAddRequest;
import com.upedge.oms.modules.order.request.StoreOrderRefundItemListRequest;
import com.upedge.oms.modules.order.request.StoreOrderRefundItemUpdateRequest;
import com.upedge.oms.modules.order.response.*;
import com.upedge.oms.modules.order.service.StoreOrderRefundItemService;
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
@RequestMapping("/storeOrderRefundItem")
public class StoreOrderRefundItemController {
    @Autowired
    private StoreOrderRefundItemService storeOrderRefundItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:storeorderrefunditem:info:id")
    public StoreOrderRefundItemInfoResponse info(@PathVariable Long id) {
        StoreOrderRefundItem result = storeOrderRefundItemService.selectByPrimaryKey(id);
        StoreOrderRefundItemInfoResponse res = new StoreOrderRefundItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefunditem:list")
    public StoreOrderRefundItemListResponse list(@RequestBody @Valid StoreOrderRefundItemListRequest request) {
        List<StoreOrderRefundItem> results = storeOrderRefundItemService.select(request);
        Long total = storeOrderRefundItemService.count(request);
        request.setTotal(total);
        StoreOrderRefundItemListResponse res = new StoreOrderRefundItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefunditem:add")
    public StoreOrderRefundItemAddResponse add(@RequestBody @Valid StoreOrderRefundItemAddRequest request) {
        StoreOrderRefundItem entity=request.toStoreOrderRefundItem();
        storeOrderRefundItemService.insertSelective(entity);
        StoreOrderRefundItemAddResponse res = new StoreOrderRefundItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefunditem:del:id")
    public StoreOrderRefundItemDelResponse del(@PathVariable Long id) {
        storeOrderRefundItemService.deleteByPrimaryKey(id);
        StoreOrderRefundItemDelResponse res = new StoreOrderRefundItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderrefunditem:update")
    public StoreOrderRefundItemUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StoreOrderRefundItemUpdateRequest request) {
        StoreOrderRefundItem entity=request.toStoreOrderRefundItem(id);
        storeOrderRefundItemService.updateByPrimaryKeySelective(entity);
        StoreOrderRefundItemUpdateResponse res = new StoreOrderRefundItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
