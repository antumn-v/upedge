package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "采购管理")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @ApiOperation("采购建议")
    @PostMapping("/adviceList/{warehouseCode}")
    public BaseResponse adviceList(@PathVariable String warehouseCode){
        return purchaseService.purchaseAdvice(warehouseCode);
    }



}
