package com.upedge.sms.modules.overseaWarehouse.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemAddRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemUpdateRequest;

import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderItemAddResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderItemDelResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderItemInfoResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderItemListResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderItemUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/overseaWarehouseServiceOrderItem")
public class OverseaWarehouseServiceOrderItemController {
    @Autowired
    private OverseaWarehouseServiceOrderItemService overseaWarehouseServiceOrderItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:info:id")
    public OverseaWarehouseServiceOrderItemInfoResponse info(@PathVariable Long id) {
        OverseaWarehouseServiceOrderItem result = overseaWarehouseServiceOrderItemService.selectByPrimaryKey(id);
        OverseaWarehouseServiceOrderItemInfoResponse res = new OverseaWarehouseServiceOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:list")
    public OverseaWarehouseServiceOrderItemListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderItemListRequest request) {
        List<OverseaWarehouseServiceOrderItem> results = overseaWarehouseServiceOrderItemService.select(request);
        Long total = overseaWarehouseServiceOrderItemService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderItemListResponse res = new OverseaWarehouseServiceOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:add")
    public OverseaWarehouseServiceOrderItemAddResponse add(@RequestBody @Valid OverseaWarehouseServiceOrderItemAddRequest request) {
        OverseaWarehouseServiceOrderItem entity=request.toOverseaWarehouseServiceOrderItem();
        overseaWarehouseServiceOrderItemService.insertSelective(entity);
        OverseaWarehouseServiceOrderItemAddResponse res = new OverseaWarehouseServiceOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:del:id")
    public OverseaWarehouseServiceOrderItemDelResponse del(@PathVariable Long id) {
        overseaWarehouseServiceOrderItemService.deleteByPrimaryKey(id);
        OverseaWarehouseServiceOrderItemDelResponse res = new OverseaWarehouseServiceOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:update")
    public OverseaWarehouseServiceOrderItemUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OverseaWarehouseServiceOrderItemUpdateRequest request) {
        OverseaWarehouseServiceOrderItem entity=request.toOverseaWarehouseServiceOrderItem(id);
        overseaWarehouseServiceOrderItemService.updateByPrimaryKeySelective(entity);
        OverseaWarehouseServiceOrderItemUpdateResponse res = new OverseaWarehouseServiceOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
