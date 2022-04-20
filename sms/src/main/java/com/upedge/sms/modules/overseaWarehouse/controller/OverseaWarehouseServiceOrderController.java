package com.upedge.sms.modules.overseaWarehouse.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.request.*;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderListResponse;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderFreightService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "海外仓订单")
@RestController
@RequestMapping("/overseaWarehouseServiceOrder")
public class OverseaWarehouseServiceOrderController {
    @Autowired
    private OverseaWarehouseServiceOrderService overseaWarehouseServiceOrderService;

    @Autowired
    private OverseaWarehouseServiceOrderFreightService overseaWarehouseServiceOrderFreightService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("订单详情")
    @GetMapping("/detail/{id}")
    public BaseResponse orderDetail(@PathVariable Long id){
        OverseaWarehouseServiceOrderVo overseaWarehouseServiceOrderVo = overseaWarehouseServiceOrderService.orderDetail(id);
        return BaseResponse.success(overseaWarehouseServiceOrderVo);
    }

    @ApiOperation("创建海外仓服务订单")
    @PostMapping("/create")
    public BaseResponse createOverseaWarehouseService(@RequestBody@Valid OverseaWarehouseServiceOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return overseaWarehouseServiceOrderService.create(request,session);
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:list")
    public OverseaWarehouseServiceOrderListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderListRequest request) {
        List<OverseaWarehouseServiceOrder> results = overseaWarehouseServiceOrderService.select(request);
        Long total = overseaWarehouseServiceOrderService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderListResponse res = new OverseaWarehouseServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("修改订单运费")
    @PostMapping("/updateFreight")
    public BaseResponse updateFreight(@RequestBody@Valid OverseaWarehouseServiceOrderUpdateFreightRequest request){
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = overseaWarehouseServiceOrderService.selectByPrimaryKey(request.getOrderId());
        if (null == overseaWarehouseServiceOrder
        || overseaWarehouseServiceOrder.getPayState() != 0){
            return BaseResponse.failed("订单不存在或订单已支付");
        }
        List<OverseaWarehouseServiceOrderFreight> orderFreights = request.getOrderFreights();;
        for (OverseaWarehouseServiceOrderFreight orderFreight : orderFreights) {
            overseaWarehouseServiceOrderFreightService.updateByPrimaryKeySelective(orderFreight);
        }
        return BaseResponse.success();
    }

    @ApiOperation("支付订单")
    @PostMapping("/pay")
    public BaseResponse payOrder(@RequestBody@Valid OverseaWarehouseServiceOrderPayRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        String key = RedisKey.CUSTOMER_PAY_ORDER_LOCK + session.getCustomerId();
        boolean b = RedisUtil.lock(redisTemplate,key,10L,20 *1000L);
        if (!b){
            return BaseResponse.failed("There is a transaction in progress");
        }
        BaseResponse response = overseaWarehouseServiceOrderService.orderPay(request,session);
        RedisUtil.unLock(redisTemplate,key);
        return response;
    }


    @ApiOperation("审核列表")
    @PostMapping("/reviewList")
    public BaseResponse reviewList(@RequestBody @Valid OverseaWarehouseServiceOrderListRequest request){
        request.setCondition("ship_type is null");
        List<OverseaWarehouseServiceOrder> overseaWarehouseServiceOrders = overseaWarehouseServiceOrderService.select(request);
//        List<OverseaWarehouseServiceOrderVo> overseaWarehouseServiceOrderVos = overseaWarehouseServiceOrderService.orderList(request);
        Long total = overseaWarehouseServiceOrderService.count(request);
        request.setTotal(total);
        BaseResponse res = new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,overseaWarehouseServiceOrders,request);
        return res;
    }

//    @ApiOperation("创建fpx入库委托单")
//    @PostMapping("/createFpxInbound/{orderId}")
    public BaseResponse createFpxInbound(@PathVariable Long orderId){
        Session session = UserUtil.getSession(redisTemplate);
        return overseaWarehouseServiceOrderService.createFpxInbound(orderId,session);
    }

    @ApiOperation("更新物流信息")
    @PostMapping("/updateTracking")
    public BaseResponse updateTracking(@RequestBody@Valid OverseaWarehouseServiceOrderUpdateTrackingRequest request){
        return overseaWarehouseServiceOrderService.updateTrackingCode(request);
    }

    @ApiOperation("确认收货")
    @PostMapping("/confirmReceipt")
    public BaseResponse confirmReceipt(@RequestBody@Valid OverseaWarehouseServiceOrderReceiptRequest request){
        String key = RedisKey.LOCK_OVERSEA_WAREHOUSE_SERVICE_ORDER_RECEIPT + request.getOrderId();
        boolean b = RedisUtil.lock(redisTemplate,key,10L,10 * 1000L);
        if (!b){
            return BaseResponse.failed("订单操作中，请稍候");
        }
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = overseaWarehouseServiceOrderService.confirmReceipt(request,session);
        RedisUtil.unLock(redisTemplate,key);
        return response;
    }
}
