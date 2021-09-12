package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.UrlUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.request.ImportFrom1688Request;
import com.upedge.pms.modules.product.vo.ProductVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.Product;
import com.upedge.pms.modules.product.service.ProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.StringJoiner;

import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.ProductAddRequest;
import com.upedge.pms.modules.product.request.ProductListRequest;
import com.upedge.pms.modules.product.request.ProductUpdateRequest;

import com.upedge.pms.modules.product.response.ProductAddResponse;
import com.upedge.pms.modules.product.response.ProductDelResponse;
import com.upedge.pms.modules.product.response.ProductInfoResponse;
import com.upedge.pms.modules.product.response.ProductListResponse;
import com.upedge.pms.modules.product.response.ProductUpdateResponse;
import javax.validation.Valid;

/**
 * 商品信息
 *
 * @author gx
 */
@Api(tags = "产品管理")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("1688导入产品")
    @RequestMapping(value="/importFrom1688", method=RequestMethod.POST)
    public BaseResponse importFrom1688(@RequestBody ImportFrom1688Request request) {
        if(!StringUtils.isBlank(request.getUrl())){
            String aliProductId= UrlUtils.getNameByUrl(request.getUrl());
            request.setOriginalProductId(aliProductId);
        }
        if(StringUtils.isBlank(request.getOriginalProductId())){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Product p=productService.selectByProductSku(request.getOriginalProductId());
        if(p!=null){
            return BaseResponse.success();
        }
        AlibabaProductVo AlibabaProductVo= Ali1688Service.getProduct(request.getOriginalProductId());
        Session session = UserUtil.getSession(redisTemplate);
        if(AlibabaProductVo==null){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        try {
            return productService.importFrom1688(AlibabaProductVo,session);
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }
    }

    @ApiOperation("产品详情")
    @GetMapping("/detail/{id}")
    public BaseResponse productDetail(@PathVariable Long id) {
        ProductVo productVo = productService.productDetail(id);
        return BaseResponse.success(productVo);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:product:info:id")
    public ProductInfoResponse info(@PathVariable Long id) {
        Product result = productService.selectByPrimaryKey(id);
        ProductInfoResponse res = new ProductInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:product:list")
    public ProductListResponse list(@RequestBody @Valid ProductListRequest request) {
        List<Product> results = productService.select(request);
        Long total = productService.count(request);
        request.setTotal(total);
        ProductListResponse res = new ProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:product:update")
    public ProductUpdateResponse update(@PathVariable Long id,@RequestBody @Valid ProductUpdateRequest request) {
        Product entity=request.toProduct(id);
        productService.updateByPrimaryKeySelective(entity);
        ProductUpdateResponse res = new ProductUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
