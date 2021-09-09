package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import com.upedge.pms.modules.product.service.ProductVariantAttrService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductVariantAttrAddRequest;
import com.upedge.pms.modules.product.request.ProductVariantAttrListRequest;
import com.upedge.pms.modules.product.request.ProductVariantAttrUpdateRequest;

import com.upedge.pms.modules.product.response.ProductVariantAttrAddResponse;
import com.upedge.pms.modules.product.response.ProductVariantAttrDelResponse;
import com.upedge.pms.modules.product.response.ProductVariantAttrInfoResponse;
import com.upedge.pms.modules.product.response.ProductVariantAttrListResponse;
import com.upedge.pms.modules.product.response.ProductVariantAttrUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/productVariantAttr")
public class ProductVariantAttrController {
    @Autowired
    private ProductVariantAttrService productVariantAttrService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productvariantattr:info:id")
    public ProductVariantAttrInfoResponse info(@PathVariable Long id) {
        ProductVariantAttr result = productVariantAttrService.selectByPrimaryKey(id);
        ProductVariantAttrInfoResponse res = new ProductVariantAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productvariantattr:list")
    public ProductVariantAttrListResponse list(@RequestBody @Valid ProductVariantAttrListRequest request) {
        List<ProductVariantAttr> results = productVariantAttrService.select(request);
        Long total = productVariantAttrService.count(request);
        request.setTotal(total);
        ProductVariantAttrListResponse res = new ProductVariantAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productvariantattr:add")
    public ProductVariantAttrAddResponse add(@RequestBody @Valid ProductVariantAttrAddRequest request) {
        ProductVariantAttr entity=request.toProductVariantAttr();
        productVariantAttrService.insertSelective(entity);
        ProductVariantAttrAddResponse res = new ProductVariantAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productvariantattr:del:id")
    public ProductVariantAttrDelResponse del(@PathVariable Long id) {
        productVariantAttrService.deleteByPrimaryKey(id);
        ProductVariantAttrDelResponse res = new ProductVariantAttrDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productvariantattr:update")
    public ProductVariantAttrUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductVariantAttrUpdateRequest request) {
        ProductVariantAttr entity=request.toProductVariantAttr(id);
        productVariantAttrService.updateByPrimaryKeySelective(entity);
        ProductVariantAttrUpdateResponse res = new ProductVariantAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
