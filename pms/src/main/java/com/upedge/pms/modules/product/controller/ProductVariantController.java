package com.upedge.pms.modules.product.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.ProductService;
import com.upedge.pms.modules.product.service.ProductVariantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 商品变体表
 *
 * @author gx
 */
@Api(tags = "产品变体")
@RestController
@RequestMapping("/productVariant")
public class ProductVariantController {
    @Autowired
    private ProductVariantService productVariantService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    ProductService productService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    @RequestMapping(value="/listVariantByIds", method=RequestMethod.POST)
    public ProductVariantsResponse listVariantByIds(@RequestBody ListVariantsRequest request) {
        return productVariantService.listVariantByIds(request.getVariantIds());
    }


    /**
     * 更新变体属性
     */
    @ApiOperation("更新变体属性")
    @RequestMapping(value="/updateAttr", method=RequestMethod.POST)
    public ProductVariantUpdateAttrResponse updateAttr(@RequestBody @Valid ProductVariantUpdateAttrRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productVariantService.updateAttr(request);
    }

    /**
     * 更新实重
     * @param request
     * @return
     */
    @ApiOperation("更新实重")
    @RequestMapping(value="/updateWeight", method=RequestMethod.POST)
    public ProductVariantUpdateWeightResponse updateWeight(@RequestBody @Valid ProductVariantUpdateWeightRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        ProductVariantUpdateWeightResponse response = null;
        try {
            response = productVariantService.updateWeight(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new ProductVariantUpdateWeightResponse(ResultCode.FAIL_CODE,e.getMessage());
        }

        return response;
    }

    /**
     * 更新体积重
     * @param request
     * @return
     */
    @ApiOperation("更新体积重")
    @RequestMapping(value="/updateVolume", method=RequestMethod.POST)
    public ProductVariantUpdateVolumeWeightResponse updateVolumeWeight(@RequestBody @Valid ProductVariantUpdateVolumeWeightRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        ProductVariantUpdateVolumeWeightResponse response = null;
        try {
            response = productVariantService.updateVolumeWeight(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new ProductVariantUpdateVolumeWeightResponse(ResultCode.FAIL_CODE,e.getMessage());
        }

        return response;
    }

    /**
     * 更新变体价格
     */
    @ApiOperation("更新变体价格")
    @RequestMapping(value="/updatePrice", method=RequestMethod.POST)
    public ProductVariantUpdatePriceResponse updatePrice(@RequestBody @Valid ProductVariantUpdatePriceRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        ProductVariantUpdatePriceResponse response = null;
        try {
            response = productVariantService.updatePrice(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new ProductVariantUpdatePriceResponse(ResultCode.FAIL_CODE,e.getMessage());
        }
        return response;
    }


    /**
     * 更新变体图片
     * @param request
     * @return
     */
    @ApiOperation("更新变体图片")
    @RequestMapping(value="/updateImage", method=RequestMethod.POST)
    public ProductVariantUpdateVariantImageResponse updateVariantImage(@RequestBody @Valid ProductVariantUpdateVariantImageRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productVariantService.updateVariantImage(request,session);
    }

    /**
     * 启用变体
     * @param request
     * @return
     */
    @ApiOperation("启用变体")
    @RequestMapping(value="/enable", method=RequestMethod.POST)
    public ProductVariantEnableResponse enableVariant(@RequestBody @Valid ProductVariantEnableRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productVariantService.enableVariant(request);
    }

    /**
     * 禁用变体
     * @param request
     * @return
     */
    @ApiOperation("禁用变体")
    @RequestMapping(value="/disable", method=RequestMethod.POST)
    public ProductVariantDisableResponse disableVariant(@RequestBody @Valid ProductVariantDisableRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return productVariantService.disableVariant(request);
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

    @ApiOperation("新增变体")
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
