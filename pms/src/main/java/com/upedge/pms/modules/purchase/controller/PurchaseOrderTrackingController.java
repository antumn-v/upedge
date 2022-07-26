package com.upedge.pms.modules.purchase.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import com.upedge.pms.modules.purchase.service.PurchaseOrderTrackingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.purchase.request.PurchaseOrderTrackingAddRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderTrackingListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderTrackingUpdateRequest;

import com.upedge.pms.modules.purchase.response.PurchaseOrderTrackingAddResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderTrackingDelResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderTrackingInfoResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderTrackingListResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderTrackingUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/purchaseOrderTracking")
public class PurchaseOrderTrackingController {
    @Autowired
    private PurchaseOrderTrackingService purchaseOrderTrackingService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:purchaseordertracking:info:id")
    public PurchaseOrderTrackingInfoResponse info(@PathVariable Long id) {
        PurchaseOrderTracking result = purchaseOrderTrackingService.selectByPrimaryKey(id);
        PurchaseOrderTrackingInfoResponse res = new PurchaseOrderTrackingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseordertracking:list")
    public PurchaseOrderTrackingListResponse list(@RequestBody @Valid PurchaseOrderTrackingListRequest request) {
        List<PurchaseOrderTracking> results = purchaseOrderTrackingService.select(request);
        Long total = purchaseOrderTrackingService.count(request);
        request.setTotal(total);
        PurchaseOrderTrackingListResponse res = new PurchaseOrderTrackingListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseordertracking:add")
    public PurchaseOrderTrackingAddResponse add(@RequestBody @Valid PurchaseOrderTrackingAddRequest request) {
        PurchaseOrderTracking entity=request.toPurchaseOrderTracking();
        purchaseOrderTrackingService.insertSelective(entity);
        PurchaseOrderTrackingAddResponse res = new PurchaseOrderTrackingAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseordertracking:del:id")
    public PurchaseOrderTrackingDelResponse del(@PathVariable Long id) {
        purchaseOrderTrackingService.deleteByPrimaryKey(id);
        PurchaseOrderTrackingDelResponse res = new PurchaseOrderTrackingDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseordertracking:update")
    public PurchaseOrderTrackingUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PurchaseOrderTrackingUpdateRequest request) {
        PurchaseOrderTracking entity=request.toPurchaseOrderTracking(id);
        purchaseOrderTrackingService.updateByPrimaryKeySelective(entity);
        PurchaseOrderTrackingUpdateResponse res = new PurchaseOrderTrackingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
