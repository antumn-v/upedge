package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "采购管理")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("采购建议")
    @PostMapping("/adviceList/{warehouseCode}")
    public BaseResponse adviceList(@PathVariable String warehouseCode){
        return purchaseService.purchaseAdvice(warehouseCode);
    }

    @ApiOperation("预览创建1688采购单")
    @PostMapping("/createOrderPreview")
    public BaseResponse createOrderPreview(@RequestBody@Valid PurchaseOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.previewPurchaseOrder(request,session);
    }


    @ApiOperation("创建1688采购单,暂不对接")
    @PostMapping("/createOrder")
    public BaseResponse createOrder(@RequestBody@Valid PurchaseOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.createPurchaseOrder(request,session);
    }



}
