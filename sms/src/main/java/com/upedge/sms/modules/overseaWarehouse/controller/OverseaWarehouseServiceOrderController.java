package com.upedge.sms.modules.overseaWarehouse.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderAddRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderUpdateRequest;

import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderAddResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderDelResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderInfoResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderListResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/overseaWarehouseServiceOrder")
public class OverseaWarehouseServiceOrderController {
    @Autowired
    private OverseaWarehouseServiceOrderService overseaWarehouseServiceOrderService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:info:id")
    public OverseaWarehouseServiceOrderInfoResponse info(@PathVariable Long id) {
        OverseaWarehouseServiceOrder result = overseaWarehouseServiceOrderService.selectByPrimaryKey(id);
        OverseaWarehouseServiceOrderInfoResponse res = new OverseaWarehouseServiceOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:list")
    public OverseaWarehouseServiceOrderListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderListRequest request) {
        List<OverseaWarehouseServiceOrder> results = overseaWarehouseServiceOrderService.select(request);
        Long total = overseaWarehouseServiceOrderService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderListResponse res = new OverseaWarehouseServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:add")
    public OverseaWarehouseServiceOrderAddResponse add(@RequestBody @Valid OverseaWarehouseServiceOrderAddRequest request) {
        OverseaWarehouseServiceOrder entity=request.toOverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrderService.insertSelective(entity);
        OverseaWarehouseServiceOrderAddResponse res = new OverseaWarehouseServiceOrderAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:del:id")
    public OverseaWarehouseServiceOrderDelResponse del(@PathVariable Long id) {
        overseaWarehouseServiceOrderService.deleteByPrimaryKey(id);
        OverseaWarehouseServiceOrderDelResponse res = new OverseaWarehouseServiceOrderDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:update")
    public OverseaWarehouseServiceOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OverseaWarehouseServiceOrderUpdateRequest request) {
        OverseaWarehouseServiceOrder entity=request.toOverseaWarehouseServiceOrder(id);
        overseaWarehouseServiceOrderService.updateByPrimaryKeySelective(entity);
        OverseaWarehouseServiceOrderUpdateResponse res = new OverseaWarehouseServiceOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
