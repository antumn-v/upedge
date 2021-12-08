package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.product.request.StoreProductListRequest;
import com.upedge.pms.modules.product.response.StoreProductListResponse;
import com.upedge.pms.modules.product.service.StoreProductService;
import com.upedge.pms.modules.product.vo.StoreProductRelateVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 海桐
 */
@Api("店铺产品")
@RestController
@RequestMapping("/store/product")
public class StoreProductController {

    @Autowired
    StoreProductService storeProductService;

    @ApiOperation("店铺产品列表")
    @PostMapping("/list")
    public StoreProductListResponse storeProductList(@RequestBody StoreProductListRequest request){
        return storeProductService.storeProductList(request);
    }

    @ApiOperation("店铺产品列表数量")
    @PostMapping("/count")
    public BaseResponse storeProductListCount(@RequestBody StoreProductListRequest request){
        return storeProductService.storeProductListCount(request);
    }

    @ApiOperation("店铺产品关联详情")
    @GetMapping("/{id}/relateDetail")
    public BaseResponse storeProductRelateDetail(@PathVariable Long id){
        List<StoreProductRelateVo> storeProductRelateVos = storeProductService.selectStoreVariantRelateDetail(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,storeProductRelateVos);
    }
}
