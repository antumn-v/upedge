package com.upedge.tms.modules.warehouse.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import com.upedge.tms.modules.warehouse.service.CountryAvailableWarehouseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.tms.modules.warehouse.request.CountryAvailableWarehouseAddRequest;
import com.upedge.tms.modules.warehouse.request.CountryAvailableWarehouseListRequest;
import com.upedge.tms.modules.warehouse.request.CountryAvailableWarehouseUpdateRequest;

import com.upedge.tms.modules.warehouse.response.CountryAvailableWarehouseAddResponse;
import com.upedge.tms.modules.warehouse.response.CountryAvailableWarehouseDelResponse;
import com.upedge.tms.modules.warehouse.response.CountryAvailableWarehouseInfoResponse;
import com.upedge.tms.modules.warehouse.response.CountryAvailableWarehouseListResponse;
import com.upedge.tms.modules.warehouse.response.CountryAvailableWarehouseUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/countryAvailableWarehouse")
public class CountryAvailableWarehouseController {
    @Autowired
    private CountryAvailableWarehouseService countryAvailableWarehouseService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "warehouse:countryavailablewarehouse:info:id")
    public CountryAvailableWarehouseInfoResponse info(@PathVariable String id) {
        CountryAvailableWarehouse result = countryAvailableWarehouseService.selectByPrimaryKey(id);
        CountryAvailableWarehouseInfoResponse res = new CountryAvailableWarehouseInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "warehouse:countryavailablewarehouse:list")
    public CountryAvailableWarehouseListResponse list(@RequestBody @Valid CountryAvailableWarehouseListRequest request) {
        List<CountryAvailableWarehouse> results = countryAvailableWarehouseService.select(request);
        Long total = countryAvailableWarehouseService.count(request);
        request.setTotal(total);
        CountryAvailableWarehouseListResponse res = new CountryAvailableWarehouseListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "warehouse:countryavailablewarehouse:add")
    public CountryAvailableWarehouseAddResponse add(@RequestBody @Valid CountryAvailableWarehouseAddRequest request) {
        CountryAvailableWarehouse entity=request.toCountryAvailableWarehouse();
        countryAvailableWarehouseService.insertSelective(entity);
        CountryAvailableWarehouseAddResponse res = new CountryAvailableWarehouseAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "warehouse:countryavailablewarehouse:del:id")
    public CountryAvailableWarehouseDelResponse del(@PathVariable String id) {
        countryAvailableWarehouseService.deleteByPrimaryKey(id);
        CountryAvailableWarehouseDelResponse res = new CountryAvailableWarehouseDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "warehouse:countryavailablewarehouse:update")
    public CountryAvailableWarehouseUpdateResponse update(@PathVariable String id,@RequestBody @Valid CountryAvailableWarehouseUpdateRequest request) {
        CountryAvailableWarehouse entity=request.toCountryAvailableWarehouse(id);
        countryAvailableWarehouseService.updateByPrimaryKeySelective(entity);
        CountryAvailableWarehouseUpdateResponse res = new CountryAvailableWarehouseUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
