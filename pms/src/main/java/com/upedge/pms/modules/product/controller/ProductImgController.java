package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.product.entity.ProductImg;
import com.upedge.pms.modules.product.request.ProductImgAddRequest;
import com.upedge.pms.modules.product.request.ProductImgListRequest;
import com.upedge.pms.modules.product.request.ProductImgUpdateRequest;
import com.upedge.pms.modules.product.request.ProductUploadImageRequest;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ProductImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品图片表
 *
 * @author gx
 */
@Api(tags = "产品图片")
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

    @ApiOperation("删除图片")
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:del:id")
    public ProductImgDelResponse del(@PathVariable Long id) {
        productImgService.deleteByPrimaryKey(id);
        ProductImgDelResponse res = new ProductImgDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("图片修改")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:productimg:update")
    public ProductImgUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductImgUpdateRequest request) {
        ProductImg entity=request.toProductImg(id);
        productImgService.updateByPrimaryKeySelective(entity);
        ProductImgUpdateResponse res = new ProductImgUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public BaseResponse uploadImage(@RequestBody@Valid ProductUploadImageRequest request){
        return productImgService.uploadImage(request);
    }


}
