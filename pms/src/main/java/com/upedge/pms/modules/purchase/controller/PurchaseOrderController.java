package com.upedge.pms.modules.purchase.controller;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.request.PurchaseOrderEditStateUpdateRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderReceiveRequest;
import com.upedge.pms.modules.purchase.response.PurchaseOrderListResponse;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
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
    public PurchaseOrderListResponse list(@RequestBody @Valid PurchaseOrderListRequest request) {
        List<PurchaseOrderVo> results = purchaseOrderService.orderList(request);
        Long total = purchaseOrderService.countPurchaseOrder(request);
        request.setTotal(total);
        PurchaseOrderListResponse res = new PurchaseOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
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
}
