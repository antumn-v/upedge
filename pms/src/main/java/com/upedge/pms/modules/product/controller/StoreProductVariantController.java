package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import com.upedge.pms.modules.product.request.*;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author author
 */
@Api(tags = "店铺产品子体管理")
@RestController
@RequestMapping("/storeVariant")
public class StoreProductVariantController {
    @Autowired
    private StoreProductVariantService storeProductVariantService;

    @Autowired
    StoreProductService storeProductService;

    @Autowired
    RedisTemplate redisTemplate;


    @PostMapping("/selectByPlatId")
    public BaseResponse selectByPlatId(@RequestBody PlatIdSelectStoreVariantRequest request){
        List<StoreProductVariantVo> variantVos = storeProductService.selectVariantByPlatId(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE,variantVos);
    }

    @ApiOperation("查询客户未拆分的变体")
    @PostMapping("/unSplitList")
    public BaseResponse listCustomerUnSplitVariant(@RequestBody StoreProductVariantUnSplitListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setCustomerId(session.getCustomerId());
        List<StoreProductVariant> variants = storeProductVariantService.selectCustomerUnSplitVariant(request);
        Long total = storeProductVariantService.countCustomerUnSplitVariant(request);
        request.setTotal(total);
        return BaseResponse.success(variants,request);
    }

    @ApiOperation("店铺产品子体列表")
    @PostMapping("/list")
    public BaseResponse listStoreProductVariant(@RequestBody StoreProductVariantListRequest request){

        List<StoreProductVariant> variants =  storeProductVariantService.select(request);

        Long count = storeProductVariantService.count(request);
        request.setTotal(count);
        return BaseResponse.success(variants,request);
    }


    @ApiOperation("拆分变体")
    @PostMapping("/splitAdd")
    public BaseResponse splitVariant(@RequestBody@Valid StoreProductVariantSplitRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return storeProductVariantService.variantSplit(request,session);
    }

    @ApiOperation("删除拆分子体")
    @PostMapping("/splitDelete/{id}")
    public BaseResponse deleteSplitVariant(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return storeProductVariantService.splitVariantDelete(id,session);
    }

    @ApiOperation("/修改拆分子体ID")
    @PostMapping("/splitUpdate")
    public BaseResponse updateSplitVariant(@RequestBody StoreSplitVariantUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return storeProductVariantService.splitVariantUpdate(request,session);
    }

    @ApiOperation("拆分子体详情")
    @PostMapping("/splitDetail/{storeVariantId}")
    public BaseResponse splitDetail(@PathVariable Long storeVariantId){
        List<StoreProductVariant> storeProductVariants = storeProductVariantService.selectSplitVariantByParentId(storeVariantId);
        return BaseResponse.success(storeProductVariants);
    }

    @ApiOperation("店铺子体报价")
    @PostMapping("/quote")
    public BaseResponse variantQuote(@RequestBody@Valid StoreProductVariantQuoteRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return storeProductVariantService.variantQuote(request,session);
    }


//    @PostMapping("/test")
    public BaseResponse test(){
        Map<Long,Long> map = new HashMap<>();
        List<StoreProductVariant> storeProductVariants = storeProductVariantService.selectWrongParentVariants();
        for (StoreProductVariant storeProductVariant : storeProductVariants) {
            List<StoreProductVariant> variants = storeProductVariantService.selectSplitVariantsByPlatVariantId(storeProductVariant.getPlatVariantId());
            if (ListUtils.isEmpty(variants)){
                continue;
            }
            StoreProductVariant parent = storeProductVariantService.selectByPlatId(storeProductVariant.getPlatVariantId());
            if (parent == null){
                continue;
            }
            StoreProductVariant variant = variants.get(0);
            if (!variant.getParentVariantId().equals(parent.getId())){
                map.put(storeProductVariant.getId(),variant.getParentVariantId());
                storeProductVariantService.updateId(parent.getId(),variant.getParentVariantId());
            }
        }
        return BaseResponse.success(map);
    }


}
