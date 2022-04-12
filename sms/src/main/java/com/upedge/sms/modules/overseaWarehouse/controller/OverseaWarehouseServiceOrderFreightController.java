package com.upedge.sms.modules.overseaWarehouse.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderFreightService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderFreightAddRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderFreightListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderFreightUpdateRequest;

import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderFreightAddResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderFreightDelResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderFreightInfoResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderFreightListResponse;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderFreightUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/overseaWarehouseServiceOrderFreight")
public class OverseaWarehouseServiceOrderFreightController {
    @Autowired
    private OverseaWarehouseServiceOrderFreightService overseaWarehouseServiceOrderFreightService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderfreight:info:id")
    public OverseaWarehouseServiceOrderFreightInfoResponse info(@PathVariable Long id) {
        OverseaWarehouseServiceOrderFreight result = overseaWarehouseServiceOrderFreightService.selectByPrimaryKey(id);
        OverseaWarehouseServiceOrderFreightInfoResponse res = new OverseaWarehouseServiceOrderFreightInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderfreight:list")
    public OverseaWarehouseServiceOrderFreightListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderFreightListRequest request) {
        List<OverseaWarehouseServiceOrderFreight> results = overseaWarehouseServiceOrderFreightService.select(request);
        Long total = overseaWarehouseServiceOrderFreightService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderFreightListResponse res = new OverseaWarehouseServiceOrderFreightListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderfreight:add")
    public OverseaWarehouseServiceOrderFreightAddResponse add(@RequestBody @Valid OverseaWarehouseServiceOrderFreightAddRequest request) {
        OverseaWarehouseServiceOrderFreight entity=request.toOverseaWarehouseServiceOrderFreight();
        overseaWarehouseServiceOrderFreightService.insertSelective(entity);
        OverseaWarehouseServiceOrderFreightAddResponse res = new OverseaWarehouseServiceOrderFreightAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderfreight:del:id")
    public OverseaWarehouseServiceOrderFreightDelResponse del(@PathVariable Long id) {
        overseaWarehouseServiceOrderFreightService.deleteByPrimaryKey(id);
        OverseaWarehouseServiceOrderFreightDelResponse res = new OverseaWarehouseServiceOrderFreightDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderfreight:update")
    public OverseaWarehouseServiceOrderFreightUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OverseaWarehouseServiceOrderFreightUpdateRequest request) {
        OverseaWarehouseServiceOrderFreight entity=request.toOverseaWarehouseServiceOrderFreight(id);
        overseaWarehouseServiceOrderFreightService.updateByPrimaryKeySelective(entity);
        OverseaWarehouseServiceOrderFreightUpdateResponse res = new OverseaWarehouseServiceOrderFreightUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
