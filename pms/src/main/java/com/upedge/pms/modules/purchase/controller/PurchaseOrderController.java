package com.upedge.pms.modules.purchase.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.purchase.request.PurchaseOrderAddRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderUpdateRequest;

import com.upedge.pms.modules.purchase.response.PurchaseOrderAddResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderDelResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderInfoResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderListResponse;
import com.upedge.pms.modules.purchase.response.PurchaseOrderUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:purchaseorder:info:id")
    public PurchaseOrderInfoResponse info(@PathVariable Long id) {
        PurchaseOrder result = purchaseOrderService.selectByPrimaryKey(id);
        PurchaseOrderInfoResponse res = new PurchaseOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorder:list")
    public PurchaseOrderListResponse list(@RequestBody @Valid PurchaseOrderListRequest request) {
        List<PurchaseOrder> results = purchaseOrderService.select(request);
        Long total = purchaseOrderService.count(request);
        request.setTotal(total);
        PurchaseOrderListResponse res = new PurchaseOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorder:add")
    public PurchaseOrderAddResponse add(@RequestBody @Valid PurchaseOrderAddRequest request) {
        PurchaseOrder entity=request.toPurchaseOrder();
        purchaseOrderService.insertSelective(entity);
        PurchaseOrderAddResponse res = new PurchaseOrderAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorder:del:id")
    public PurchaseOrderDelResponse del(@PathVariable Long id) {
        purchaseOrderService.deleteByPrimaryKey(id);
        PurchaseOrderDelResponse res = new PurchaseOrderDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorder:update")
    public PurchaseOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid PurchaseOrderUpdateRequest request) {
        PurchaseOrder entity=request.toPurchaseOrder(id);
        purchaseOrderService.updateByPrimaryKeySelective(entity);
        PurchaseOrderUpdateResponse res = new PurchaseOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
