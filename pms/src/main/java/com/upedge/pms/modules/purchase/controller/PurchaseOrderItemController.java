package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.request.*;
import com.upedge.pms.modules.purchase.response.*;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "采购订单产品")
@RestController
@RequestMapping("/purchaseOrderItem")
public class PurchaseOrderItemController {
    @Autowired
    private PurchaseOrderItemService purchaseOrderItemService;

    @ApiOperation("修改价格")
    @PostMapping("/updatePrice")
    public BaseResponse updatePrice(@RequestBody@Valid PurchaseOrderItemUpdatePriceRequest request){
        return purchaseOrderItemService.updatePriceById(request);
    }

    @ApiOperation("修改数量")
    @PostMapping("/updateQuantity")
    public BaseResponse updateQuantity(@RequestBody@Valid PurchaseOrderItemUpdateQuantityRequest request){
        return purchaseOrderItemService.updateQuantityById(request);
    }

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
