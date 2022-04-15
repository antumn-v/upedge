package com.upedge.sms.modules.overseaWarehouse.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseSkuAddRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseSkuListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseSkuUpdateRequest;
import com.upedge.sms.modules.overseaWarehouse.response.*;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/overseaWarehouseSku")
public class OverseaWarehouseSkuController {
    @Autowired
    private OverseaWarehouseSkuService overseaWarehouseSkuService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:overseawarehousesku:info:id")
    public OverseaWarehouseSkuInfoResponse info(@PathVariable Long id) {
        OverseaWarehouseSku result = overseaWarehouseSkuService.selectByPrimaryKey(id);
        OverseaWarehouseSkuInfoResponse res = new OverseaWarehouseSkuInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehousesku:list")
    public OverseaWarehouseSkuListResponse list(@RequestBody @Valid OverseaWarehouseSkuListRequest request) {
        List<OverseaWarehouseSku> results = overseaWarehouseSkuService.select(request);
        Long total = overseaWarehouseSkuService.count(request);
        request.setTotal(total);
        OverseaWarehouseSkuListResponse res = new OverseaWarehouseSkuListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehousesku:add")
    public OverseaWarehouseSkuAddResponse add(@RequestBody @Valid OverseaWarehouseSkuAddRequest request) {
        OverseaWarehouseSku entity=request.toOverseaWarehouseSku();
        overseaWarehouseSkuService.insertSelective(entity);
        OverseaWarehouseSkuAddResponse res = new OverseaWarehouseSkuAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehousesku:del:id")
    public OverseaWarehouseSkuDelResponse del(@PathVariable Long id) {
        overseaWarehouseSkuService.deleteByPrimaryKey(id);
        OverseaWarehouseSkuDelResponse res = new OverseaWarehouseSkuDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehousesku:update")
    public OverseaWarehouseSkuUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OverseaWarehouseSkuUpdateRequest request) {
        OverseaWarehouseSku entity=request.toOverseaWarehouseSku(id);
        overseaWarehouseSkuService.updateByPrimaryKeySelective(entity);
        OverseaWarehouseSkuUpdateResponse res = new OverseaWarehouseSkuUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
