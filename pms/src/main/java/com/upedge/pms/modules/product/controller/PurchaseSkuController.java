package com.upedge.pms.modules.product.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoListRequest;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "采购sku管理")
@RestController
@RequestMapping("/purchaseSku")
public class PurchaseSkuController {

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;


    @ApiOperation("采购sku列表")
    @PostMapping("/list")
    public BaseResponse list(@RequestBody ProductPurchaseInfoListRequest request){
        List<ProductPurchaseInfo> productPurchaseInfoList = productPurchaseInfoService.select(request);
        return BaseResponse.success(productPurchaseInfoList);
    }
}
