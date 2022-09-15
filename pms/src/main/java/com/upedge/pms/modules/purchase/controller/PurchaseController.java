package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Api(tags = "采购管理")
@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @ApiOperation("采购建议")
    @PostMapping("/adviceList/{warehouseCode}")
    public BaseResponse adviceList(@PathVariable String warehouseCode){
        return purchaseService.purchaseAdvice(warehouseCode);
    }

    @ApiOperation("取消采购")
    @PostMapping("/cancel")
    public BaseResponse cancelPurchase(@RequestBody List<Long> variantIds){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.cancelPurchase(variantIds,session);
    }

    @ApiOperation("恢复采购")
    @PostMapping("/restore")
    public BaseResponse restorePurchase(@RequestBody List<Long> variantIds){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.restorePurchase(variantIds,session);
    }

    @ApiOperation("预览创建1688采购单")
    @PostMapping("/createOrderPreview")
    public BaseResponse createOrderPreview(@RequestBody@Valid PurchaseOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.previewPurchaseOrder(request,session);
    }


    @ApiOperation("创建1688采购单")
    @PostMapping("/createOrder")
    public BaseResponse createOrder(@RequestBody@Valid PurchaseOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        try {
            List<Long> ids = purchaseService.createPurchaseOrder(request,session);
            for (Long id : ids) {
                CompletableFuture.runAsync(new Runnable() {
                    @Override
                    public void run() {
                        purchaseOrderService.completeOrderInfo(id);
                    }
                },threadPoolExecutor);
            }
            return BaseResponse.success();
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }
    }

}
