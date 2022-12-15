package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.request.PurchaseAdviceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/adviceList")
    public BaseResponse adviceList(@RequestBody@Valid PurchaseAdviceRequest request){
        return purchaseService.purchaseAdvice(request);
    }

    @ApiOperation("采购建议")
    @PostMapping("/adviceListCache")
    public BaseResponse adviceListCache(@RequestBody@Valid PurchaseAdviceRequest request){
        return purchaseService.purchaseAdviceCache(request);
    }

    @ApiOperation("客户待备库产品数量")
    @PostMapping("/customerCount")
    public BaseResponse customerPurchaseCount(){
        return purchaseService.customerPurchaseCount();
    }

    @ApiOperation("取消采购")
    @PostMapping("/cancel")
    public BaseResponse cancelPurchase(@RequestBody List<Long> variantIds){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.cancelPurchase(variantIds,session);
    }

    @ApiOperation("搁置采购")
    @PostMapping("/shunt")
    public BaseResponse shuntPurchase(@RequestBody List<Long> variantIds){
        List<Long> cancelIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,0,-1);
        cancelIds.removeAll(variantIds);
        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,cancelIds);

        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST,variantIds);
        return BaseResponse.success();
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

    @PostMapping("/createOrderTest")
    public BaseResponse createOrderTest(@RequestBody@Valid PurchaseOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseService.createPurchaseOrder(request,session,1);
    }

}
