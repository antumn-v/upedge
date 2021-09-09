package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductAttr;
import com.upedge.pms.modules.product.service.ProductAttrService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductAttrAddRequest;
import com.upedge.pms.modules.product.request.ProductAttrListRequest;
import com.upedge.pms.modules.product.request.ProductAttrUpdateRequest;

import com.upedge.pms.modules.product.response.ProductAttrAddResponse;
import com.upedge.pms.modules.product.response.ProductAttrDelResponse;
import com.upedge.pms.modules.product.response.ProductAttrInfoResponse;
import com.upedge.pms.modules.product.response.ProductAttrListResponse;
import com.upedge.pms.modules.product.response.ProductAttrUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/productAttr")
public class ProductAttrController {
    @Autowired
    private ProductAttrService productAttrService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productattr:info:id")
    public ProductAttrInfoResponse info(@PathVariable Long id) {
        ProductAttr result = productAttrService.selectByPrimaryKey(id);
        ProductAttrInfoResponse res = new ProductAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productattr:list")
    public ProductAttrListResponse list(@RequestBody @Valid ProductAttrListRequest request) {
        List<ProductAttr> results = productAttrService.select(request);
        Long total = productAttrService.count(request);
        request.setTotal(total);
        ProductAttrListResponse res = new ProductAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productattr:add")
    public ProductAttrAddResponse add(@RequestBody @Valid ProductAttrAddRequest request) {
        ProductAttr entity=request.toProductAttr();
        productAttrService.insertSelective(entity);
        ProductAttrAddResponse res = new ProductAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productattr:del:id")
    public ProductAttrDelResponse del(@PathVariable Long id) {
        productAttrService.deleteByPrimaryKey(id);
        ProductAttrDelResponse res = new ProductAttrDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productattr:update")
    public ProductAttrUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductAttrUpdateRequest request) {
        ProductAttr entity=request.toProductAttr(id);
        productAttrService.updateByPrimaryKeySelective(entity);
        ProductAttrUpdateResponse res = new ProductAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
