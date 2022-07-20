package com.upedge.pms.modules.purchase.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemAddRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemUpdateRequest;

import com.upedge.pms.modules.purchase.response.PurchaseOrderItemAddResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderItemDelResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderItemInfoResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderItemListResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderItemUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/purchaseOrderItem")
public class PurchaseOrderItemController {
    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:purchaseorderitem:info:id")
    public PurchaseOrderItemInfoResponse info(@PathVariable Long id) {
        PurchaseOrderItem result = purchaseOrderItemService.selectByPrimaryKey(id);
        PurchaseOrderItemInfoResponse res = new PurchaseOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderitem:list")
    public PurchaseOrderItemListResponse list(@RequestBody @Valid PurchaseOrderItemListRequest request) {
        List<PurchaseOrderItem> results = purchaseOrderItemService.select(request);
        Long total = purchaseOrderItemService.count(request);
        request.setTotal(total);
        PurchaseOrderItemListResponse res = new PurchaseOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderitem:add")
    public PurchaseOrderItemAddResponse add(@RequestBody @Valid PurchaseOrderItemAddRequest request) {
        PurchaseOrderItem entity=request.toPurchaseOrderItem();
        purchaseOrderItemService.insertSelective(entity);
        PurchaseOrderItemAddResponse res = new PurchaseOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderitem:del:id")
    public PurchaseOrderItemDelResponse del(@PathVariable Long id) {
        purchaseOrderItemService.deleteByPrimaryKey(id);
        PurchaseOrderItemDelResponse res = new PurchaseOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderitem:update")
    public PurchaseOrderItemUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PurchaseOrderItemUpdateRequest request) {
        PurchaseOrderItem entity=request.toPurchaseOrderItem(id);
        purchaseOrderItemService.updateByPrimaryKeySelective(entity);
        PurchaseOrderItemUpdateResponse res = new PurchaseOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
