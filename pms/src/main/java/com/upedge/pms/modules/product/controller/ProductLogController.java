package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductLog;
import com.upedge.pms.modules.product.service.ProductLogService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductLogAddRequest;
import com.upedge.pms.modules.product.request.ProductLogListRequest;
import com.upedge.pms.modules.product.request.ProductLogUpdateRequest;

import com.upedge.pms.modules.product.response.ProductLogAddResponse;
import com.upedge.pms.modules.product.response.ProductLogDelResponse;
import com.upedge.pms.modules.product.response.ProductLogInfoResponse;
import com.upedge.pms.modules.product.response.ProductLogListResponse;
import com.upedge.pms.modules.product.response.ProductLogUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/productLog")
public class ProductLogController {
    @Autowired
    private ProductLogService productLogService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productlog:info:id")
    public ProductLogInfoResponse info(@PathVariable Long id) {
        ProductLog result = productLogService.selectByPrimaryKey(id);
        ProductLogInfoResponse res = new ProductLogInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productlog:list")
    public ProductLogListResponse list(@RequestBody @Valid ProductLogListRequest request) {
        List<ProductLog> results = productLogService.select(request);
        Long total = productLogService.count(request);
        request.setTotal(total);
        ProductLogListResponse res = new ProductLogListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productlog:add")
    public ProductLogAddResponse add(@RequestBody @Valid ProductLogAddRequest request) {
        ProductLog entity=request.toProductLog();
        productLogService.insertSelective(entity);
        ProductLogAddResponse res = new ProductLogAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productlog:del:id")
    public ProductLogDelResponse del(@PathVariable Long id) {
        productLogService.deleteByPrimaryKey(id);
        ProductLogDelResponse res = new ProductLogDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productlog:update")
    public ProductLogUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductLogUpdateRequest request) {
        ProductLog entity=request.toProductLog(id);
        productLogService.updateByPrimaryKeySelective(entity);
        ProductLogUpdateResponse res = new ProductLogUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
