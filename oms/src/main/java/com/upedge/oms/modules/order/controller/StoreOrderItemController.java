package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.product.StoreProductSalesVo;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.request.StoreOrderItemAddRequest;
import com.upedge.oms.modules.order.request.StoreOrderItemListRequest;
import com.upedge.oms.modules.order.request.StoreOrderItemUpdateRequest;
import com.upedge.oms.modules.order.response.*;
import com.upedge.oms.modules.order.service.StoreOrderItemService;
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
@RequestMapping("/storeOrderItem")
public class StoreOrderItemController {
    @Autowired
    private StoreOrderItemService storeOrderItemService;
    
    
    @GetMapping("/sales")
    public BaseResponse storeProductSales(){
        List<StoreProductSalesVo> salesVos = storeOrderItemService.selectStoreProductSales();
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,salesVos);
    }


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:storeorderitem:info:id")
    public StoreOrderItemInfoResponse info(@PathVariable Long id) {
        StoreOrderItem result = storeOrderItemService.selectByPrimaryKey(id);
        StoreOrderItemInfoResponse res = new StoreOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderitem:list")
    public StoreOrderItemListResponse list(@RequestBody @Valid StoreOrderItemListRequest request) {
        List<StoreOrderItem> results = storeOrderItemService.select(request);
        Long total = storeOrderItemService.count(request);
        request.setTotal(total);
        StoreOrderItemListResponse res = new StoreOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderitem:add")
    public StoreOrderItemAddResponse add(@RequestBody @Valid StoreOrderItemAddRequest request) {
        StoreOrderItem entity=request.toStoreOrderItem();
        storeOrderItemService.insertSelective(entity);
        StoreOrderItemAddResponse res = new StoreOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderitem:del:id")
    public StoreOrderItemDelResponse del(@PathVariable Long id) {
        storeOrderItemService.deleteByPrimaryKey(id);
        StoreOrderItemDelResponse res = new StoreOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:storeorderitem:update")
    public StoreOrderItemUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StoreOrderItemUpdateRequest request) {
        StoreOrderItem entity=request.toStoreOrderItem(id);
        storeOrderItemService.updateByPrimaryKeySelective(entity);
        StoreOrderItemUpdateResponse res = new StoreOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
