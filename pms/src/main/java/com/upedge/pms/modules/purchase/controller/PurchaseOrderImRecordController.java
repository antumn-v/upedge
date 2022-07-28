package com.upedge.pms.modules.purchase.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import com.upedge.pms.modules.purchase.service.PurchaseOrderImRecordService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImRecordAddRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImRecordListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImRecordUpdateRequest;

import com.upedge.pms.modules.purchase.response.PurchaseOrderImRecordAddResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderImRecordDelResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderImRecordInfoResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderImRecordListResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderImRecordUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/purchaseOrderImRecord")
public class PurchaseOrderImRecordController {
    @Autowired
    private PurchaseOrderImRecordService purchaseOrderImRecordService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:purchaseorderimrecord:info:id")
    public PurchaseOrderImRecordInfoResponse info(@PathVariable Long id) {
        PurchaseOrderImRecord result = purchaseOrderImRecordService.selectByPrimaryKey(id);
        PurchaseOrderImRecordInfoResponse res = new PurchaseOrderImRecordInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderimrecord:list")
    public PurchaseOrderImRecordListResponse list(@RequestBody @Valid PurchaseOrderImRecordListRequest request) {
        List<PurchaseOrderImRecord> results = purchaseOrderImRecordService.select(request);
        Long total = purchaseOrderImRecordService.count(request);
        request.setTotal(total);
        PurchaseOrderImRecordListResponse res = new PurchaseOrderImRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderimrecord:add")
    public PurchaseOrderImRecordAddResponse add(@RequestBody @Valid PurchaseOrderImRecordAddRequest request) {
        PurchaseOrderImRecord entity=request.toPurchaseOrderImRecord();
        purchaseOrderImRecordService.insertSelective(entity);
        PurchaseOrderImRecordAddResponse res = new PurchaseOrderImRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderimrecord:del:id")
    public PurchaseOrderImRecordDelResponse del(@PathVariable Long id) {
        purchaseOrderImRecordService.deleteByPrimaryKey(id);
        PurchaseOrderImRecordDelResponse res = new PurchaseOrderImRecordDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorderimrecord:update")
    public PurchaseOrderImRecordUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PurchaseOrderImRecordUpdateRequest request) {
        PurchaseOrderImRecord entity=request.toPurchaseOrderImRecord(id);
        purchaseOrderImRecordService.updateByPrimaryKeySelective(entity);
        PurchaseOrderImRecordUpdateResponse res = new PurchaseOrderImRecordUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
