package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.service.ProductImgService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductImgAddRequest;
import com.upedge.pms.modules.product.request.ProductImgListRequest;
import com.upedge.pms.modules.product.request.ProductImgUpdateRequest;

import com.upedge.pms.modules.product.response.ProductImgAddResponse;
import com.upedge.pms.modules.product.response.ProductImgDelResponse;
import com.upedge.pms.modules.product.response.ProductImgInfoResponse;
import com.upedge.pms.modules.product.response.ProductImgListResponse;
import com.upedge.pms.modules.product.response.ProductImgUpdateResponse;
import javax.validation.Valid;

/**
 * 商品图片表
 *
 * @author gx
 */
@RestController
@RequestMapping("/productImg")
public class ProductImgController {
    @Autowired
    private ProductImgService productImgService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:productimg:info:id")
    public ProductImgInfoResponse info(@PathVariable Long id) {
        ProductImg result = productImgService.selectByPrimaryKey(id);
        ProductImgInfoResponse res = new ProductImgInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:list")
    public ProductImgListResponse list(@RequestBody @Valid ProductImgListRequest request) {
        List<ProductImg> results = productImgService.select(request);
        Long total = productImgService.count(request);
        request.setTotal(total);
        ProductImgListResponse res = new ProductImgListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:add")
    public ProductImgAddResponse add(@RequestBody @Valid ProductImgAddRequest request) {
        ProductImg entity=request.toProductImg();
        productImgService.insertSelective(entity);
        ProductImgAddResponse res = new ProductImgAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:del:id")
    public ProductImgDelResponse del(@PathVariable Long id) {
        productImgService.deleteByPrimaryKey(id);
        ProductImgDelResponse res = new ProductImgDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:update")
    public ProductImgUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductImgUpdateRequest request) {
        ProductImg entity=request.toProductImg(id);
        productImgService.updateByPrimaryKeySelective(entity);
        ProductImgUpdateResponse res = new ProductImgUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
