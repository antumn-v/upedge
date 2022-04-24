package com.upedge.sms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.wholesale.WholesaleOrderVo;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderCreateRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderListRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderPayRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderUpdateRequest;
import com.upedge.sms.modules.wholesale.response.WholesaleOrderInfoResponse;
import com.upedge.sms.modules.wholesale.response.WholesaleOrderListResponse;
import com.upedge.sms.modules.wholesale.response.WholesaleOrderUpdateResponse;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "批发订单")
@RestController
@RequestMapping("/wholesaleOrder")
public class WholesaleOrderController {
    @Autowired
    private WholesaleOrderService wholesaleOrderService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("创建批发订单")
    @PostMapping("/create")
    public BaseResponse createWarehouse(@RequestBody@Valid WholesaleOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.create(request,session);
    }

    @ApiOperation("批发订单详情")
    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaleorder:info:id")
    public WholesaleOrderInfoResponse info(@PathVariable Long id) {
        WholesaleOrderVo wholesaleOrderVo = wholesaleOrderService.orderDetail(id);
        WholesaleOrderInfoResponse res = new WholesaleOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,wholesaleOrderVo,id);
        return res;
    }

    @ApiOperation("批发订单列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:list")
    public WholesaleOrderListResponse list(@RequestBody @Valid WholesaleOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getApplicationId() != Constant.ADMIN_APPLICATION_ID){
            if (null == request.getT()){
                request.setT(new WholesaleOrder());
            }
            request.getT().setCustomerId(session.getCustomerId());
        }
        List<WholesaleOrder> results = wholesaleOrderService.select(request);
        Long total = wholesaleOrderService.count(request);
        request.setTotal(total);
        WholesaleOrderListResponse res = new WholesaleOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


    @ApiOperation("修改订单物流单号")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:update")
    public WholesaleOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WholesaleOrderUpdateRequest request) {
        WholesaleOrder entity=request.toWholesaleOrder(id);
        entity.setShipState(OrderConstant.SHIP_STATE_SHIPPED);
        wholesaleOrderService.updateByPrimaryKeySelective(entity);
        WholesaleOrderUpdateResponse res = new WholesaleOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("支付批发订单")
    @PostMapping("/pay")
    public BaseResponse payOrder(@RequestBody@Valid WholesaleOrderPayRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        String key = RedisKey.CUSTOMER_PAY_ORDER_LOCK + session.getCustomerId();
        boolean b = RedisUtil.lock(redisTemplate,key,10L,20 *1000L);
        if (!b){
            return BaseResponse.failed("There is a transaction in progress");
        }
        BaseResponse response = wholesaleOrderService.payOrder(request,session);
        if (response.getCode() == ResultCode.SUCCESS_CODE){
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    wholesaleOrderService.saveTransactionRecordMessage(session.getId(), request.getOrderId());
                }
            },threadPoolExecutor);
        }
        RedisUtil.unLock(redisTemplate,key);
        return response;
    }


}
