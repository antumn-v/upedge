package com.upedge.sms.modules.photography.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import com.upedge.sms.modules.photography.service.ProductPhotographyOrderItemService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderItemAddRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderItemListRequest;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderItemUpdateRequest;

import com.upedge.sms.modules.photography.response.ProductPhotographyOrderItemAddResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderItemDelResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderItemInfoResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderItemListResponse;
import com.upedge.sms.modules.photography.response.ProductPhotographyOrderItemUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/productPhotographyOrderItem")
public class ProductPhotographyOrderItemController {
    @Autowired
    private ProductPhotographyOrderItemService productPhotographyOrderItemService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "photography:productphotographyorderitem:info:id")
    public ProductPhotographyOrderItemInfoResponse info(@PathVariable Long id) {
        ProductPhotographyOrderItem result = productPhotographyOrderItemService.selectByPrimaryKey(id);
        ProductPhotographyOrderItemInfoResponse res = new ProductPhotographyOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorderitem:list")
    public ProductPhotographyOrderItemListResponse list(@RequestBody @Valid ProductPhotographyOrderItemListRequest request) {
        List<ProductPhotographyOrderItem> results = productPhotographyOrderItemService.select(request);
        Long total = productPhotographyOrderItemService.count(request);
        request.setTotal(total);
        ProductPhotographyOrderItemListResponse res = new ProductPhotographyOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorderitem:add")
    public ProductPhotographyOrderItemAddResponse add(@RequestBody @Valid ProductPhotographyOrderItemAddRequest request) {
        ProductPhotographyOrderItem entity=request.toProductPhotographyOrderItem();
        productPhotographyOrderItemService.insertSelective(entity);
        ProductPhotographyOrderItemAddResponse res = new ProductPhotographyOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorderitem:del:id")
    public ProductPhotographyOrderItemDelResponse del(@PathVariable Long id) {
        productPhotographyOrderItemService.deleteByPrimaryKey(id);
        ProductPhotographyOrderItemDelResponse res = new ProductPhotographyOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "photography:productphotographyorderitem:update")
    public ProductPhotographyOrderItemUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductPhotographyOrderItemUpdateRequest request) {
        ProductPhotographyOrderItem entity=request.toProductPhotographyOrderItem(id);
        productPhotographyOrderItemService.updateByPrimaryKeySelective(entity);
        ProductPhotographyOrderItemUpdateResponse res = new ProductPhotographyOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
