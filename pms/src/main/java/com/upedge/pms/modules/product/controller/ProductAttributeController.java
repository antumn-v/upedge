package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductAttribute;
import com.upedge.pms.modules.product.service.ProductAttributeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductAttributeAddRequest;
import com.upedge.pms.modules.product.request.ProductAttributeListRequest;
import com.upedge.pms.modules.product.request.ProductAttributeUpdateRequest;

import com.upedge.pms.modules.product.response.ProductAttributeAddResponse;
import com.upedge.pms.modules.product.response.ProductAttributeDelResponse;
import com.upedge.pms.modules.product.response.ProductAttributeInfoResponse;
import com.upedge.pms.modules.product.response.ProductAttributeListResponse;
import com.upedge.pms.modules.product.response.ProductAttributeUpdateResponse;
import javax.validation.Valid;

/**
 * 商品属性表
 *
 * @author gx
 */
@RestController
@RequestMapping("/productAttribute")
public class ProductAttributeController {
    @Autowired
    private ProductAttributeService productAttributeService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productattribute:info:id")
    public ProductAttributeInfoResponse info(@PathVariable Long id) {
        ProductAttribute result = productAttributeService.selectByPrimaryKey(id);
        ProductAttributeInfoResponse res = new ProductAttributeInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productattribute:list")
    public ProductAttributeListResponse list(@RequestBody @Valid ProductAttributeListRequest request) {
        List<ProductAttribute> results = productAttributeService.select(request);
        Long total = productAttributeService.count(request);
        request.setTotal(total);
        ProductAttributeListResponse res = new ProductAttributeListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productattribute:add")
    public ProductAttributeAddResponse add(@RequestBody @Valid ProductAttributeAddRequest request) {
        ProductAttribute entity=request.toProductAttribute();
        productAttributeService.insertSelective(entity);
        ProductAttributeAddResponse res = new ProductAttributeAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productattribute:del:id")
    public ProductAttributeDelResponse del(@PathVariable Long id) {
        productAttributeService.deleteByPrimaryKey(id);
        ProductAttributeDelResponse res = new ProductAttributeDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productattribute:update")
    public ProductAttributeUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductAttributeUpdateRequest request) {
        ProductAttribute entity=request.toProductAttribute(id);
        productAttributeService.updateByPrimaryKeySelective(entity);
        ProductAttributeUpdateResponse res = new ProductAttributeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
