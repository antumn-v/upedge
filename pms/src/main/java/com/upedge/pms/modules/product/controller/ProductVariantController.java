package com.upedge.pms.modules.product.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.request.ProductVariantAddRequest;
import com.upedge.pms.modules.product.request.ProductVariantListRequest;
import com.upedge.pms.modules.product.request.ProductVariantUpdateRequest;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品变体表
 *
 * @author gx
 */
@RestController
@RequestMapping("/productVariant")
public class ProductVariantController {
    @Autowired
    private ProductVariantService productVariantService;


    @RequestMapping(value="/listVariantByIds", method=RequestMethod.POST)
    public ProductVariantsResponse listVariantByIds(@RequestBody ListVariantsRequest request) {
        return productVariantService.listVariantByIds(request.getVariantIds());
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productvariant:info:id")
    public ProductVariantInfoResponse info(@PathVariable Long id) {
        ProductVariant result = productVariantService.selectByPrimaryKey(id);
        ProductVariantInfoResponse res = new ProductVariantInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productvariant:list")
    public ProductVariantListResponse list(@RequestBody @Valid ProductVariantListRequest request) {
        List<ProductVariant> results = productVariantService.select(request);
        Long total = productVariantService.count(request);
        request.setTotal(total);
        ProductVariantListResponse res = new ProductVariantListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productvariant:add")
    public ProductVariantAddResponse add(@RequestBody @Valid ProductVariantAddRequest request) {
        ProductVariant entity=request.toProductVariant();
        productVariantService.insertSelective(entity);
        ProductVariantAddResponse res = new ProductVariantAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productvariant:del:id")
    public ProductVariantDelResponse del(@PathVariable Long id) {
        productVariantService.deleteByPrimaryKey(id);
        ProductVariantDelResponse res = new ProductVariantDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productvariant:update")
    public ProductVariantUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductVariantUpdateRequest request) {
        ProductVariant entity=request.toProductVariant(id);
        productVariantService.updateByPrimaryKeySelective(entity);
        ProductVariantUpdateResponse res = new ProductVariantUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
