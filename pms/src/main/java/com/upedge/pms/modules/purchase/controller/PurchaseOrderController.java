package com.upedge.pms.modules.purchase.controller;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.pms.request.CreatePurchaseOrderRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.request.PurchaseOrderEditStateUpdateRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderReceiveRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderRevokeRequest;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "采购订单")
@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value="/1688info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:purchaseorder:info:id")
    public BaseResponse info(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.selectByPrimaryKey(id);
        if (null == purchaseOrder || purchaseOrder.getPurchaseType() != 0){
            return BaseResponse.failed();
        }
        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
        try {
            alibabaOpenplatformTradeModelTradeInfo = Ali1688Service.orderDetail(Long.parseLong(purchaseOrder.getPurchaseId()), null);
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.success(alibabaOpenplatformTradeModelTradeInfo.getBaseInfo());
    }

    @ApiOperation("订单列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseorder:list")
    public BaseResponse list(@RequestBody @Valid PurchaseOrderListRequest request) {
        return purchaseOrderService.orderList(request);
    }

    @ApiOperation("订单物流信息")
    @PostMapping("/shipDetail/{id}")
    public BaseResponse purchaseOrderShipDetail(@PathVariable Long id){
        PurchaseOrder purchaseOrder = purchaseOrderService.selectByPrimaryKey(id);
        if (null == purchaseOrder){
            return BaseResponse.failed("订单不存在");
        }
        List<AlibabaLogisticsOpenPlatformLogisticsTrace> alibabaLogisticsOpenPlatformLogisticsTraces = null;
        try {
            alibabaLogisticsOpenPlatformLogisticsTraces = Ali1688Service.orderShipDetail(Long.parseLong(purchaseOrder.getPurchaseId()),null);
        } catch (CustomerException e) {
            e.printStackTrace();
        }
        if (ListUtils.isNotEmpty(alibabaLogisticsOpenPlatformLogisticsTraces)){
            return BaseResponse.success(alibabaLogisticsOpenPlatformLogisticsTraces);
        }
        return BaseResponse.success(new ArrayList<>());
    }

    @ApiOperation("同步1688订单信息")
    @PostMapping("/refresh/{id}")
    public BaseResponse refreshDetail(@PathVariable Long id){
        return purchaseOrderService.refreshFrom1688(id);
    }

    @PostMapping("/refreshAll")
    public BaseResponse refreshAll(@RequestBody List<Long> ids){
        for (Long id : ids) {
            purchaseOrderService.refreshFrom1688(id);
        }
        return BaseResponse.success();
    }

    @ApiOperation("销单入库")
    @PostMapping("/receive")
    public BaseResponse orderReceive(@RequestBody@Valid PurchaseOrderReceiveRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = null;
        try {
            response = purchaseOrderService.orderReceive(request,session);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
        return response;
    }

    @ApiOperation("修改订单编辑状态")
    @PostMapping("/updateEditState")
    public BaseResponse updateEditState(@RequestBody@Valid PurchaseOrderEditStateUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseOrderService.updateEditState(request,session);
    }

    @ApiOperation("作废采购订单")
    @PostMapping("/revoke")
    public BaseResponse revokeOrder(@RequestBody@Valid PurchaseOrderRevokeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return purchaseOrderService.revokePurchaseOrder(request,session);
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }
    }


    @PostMapping("/customCreate")
    public BaseResponse customCreate(@RequestBody CreatePurchaseOrderRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchaseOrderService.customCreate(request,session);
    }

    @PostMapping("/createByCustomerStockOrder")
    public BaseResponse createByCustomerStockOrder(@RequestBody CreatePurchaseOrderRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setPreview(true);
        BaseResponse response = purchaseOrderService.customCreate(request,session);
        if (response.getCode() == ResultCode.SUCCESS_CODE){
//            request.setPreview(false);
//            purchaseOrderService.customCreate(request,session);
        }
        return response;
    }

    @PostMapping("/completeInfo/{id}")
    public BaseResponse completeOrderInfo(@PathVariable Long id){
        purchaseOrderService.completeOrderInfo(id);
        return BaseResponse.success();
    }
}
