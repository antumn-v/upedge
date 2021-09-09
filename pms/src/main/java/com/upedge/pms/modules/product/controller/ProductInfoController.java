package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductInfo;
import com.upedge.pms.modules.product.service.ProductInfoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductInfoAddRequest;
import com.upedge.pms.modules.product.request.ProductInfoListRequest;
import com.upedge.pms.modules.product.request.ProductInfoUpdateRequest;

import com.upedge.pms.modules.product.response.ProductInfoAddResponse;
import com.upedge.pms.modules.product.response.ProductInfoDelResponse;
import com.upedge.pms.modules.product.response.ProductInfoInfoResponse;
import com.upedge.pms.modules.product.response.ProductInfoListResponse;
import com.upedge.pms.modules.product.response.ProductInfoUpdateResponse;
import javax.validation.Valid;

/**
 * 商品描述表
 *
 * @author gx
 */
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productinfo:info:id")
    public ProductInfoInfoResponse info(@PathVariable Long id) {
        ProductInfo result = productInfoService.selectByPrimaryKey(id);
        ProductInfoInfoResponse res = new ProductInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productinfo:list")
    public ProductInfoListResponse list(@RequestBody @Valid ProductInfoListRequest request) {
        List<ProductInfo> results = productInfoService.select(request);
        Long total = productInfoService.count(request);
        request.setTotal(total);
        ProductInfoListResponse res = new ProductInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productinfo:add")
    public ProductInfoAddResponse add(@RequestBody @Valid ProductInfoAddRequest request) {
        ProductInfo entity=request.toProductInfo();
        productInfoService.insertSelective(entity);
        ProductInfoAddResponse res = new ProductInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productinfo:del:id")
    public ProductInfoDelResponse del(@PathVariable Long id) {
        productInfoService.deleteByPrimaryKey(id);
        ProductInfoDelResponse res = new ProductInfoDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productinfo:update")
    public ProductInfoUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductInfoUpdateRequest request) {
        ProductInfo entity=request.toProductInfo(id);
        productInfoService.updateByPrimaryKeySelective(entity);
        ProductInfoUpdateResponse res = new ProductInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
