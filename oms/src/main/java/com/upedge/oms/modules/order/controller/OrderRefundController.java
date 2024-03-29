package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.entity.OrderRefundItem;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderApplyRefundResponse;
import com.upedge.oms.modules.order.response.OrderRefundInfoResponse;
import com.upedge.oms.modules.order.response.OrderRefundListResponse;
import com.upedge.oms.modules.order.service.OrderRefundItemService;
import com.upedge.oms.modules.order.service.OrderRefundService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.OrderRefundVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "订单退款")
@Slf4j
@RestController
@RequestMapping("/orderRefund")
public class OrderRefundController {
    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OrderRefundItemService orderRefundItemService;

    @ApiOperation("退款详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:orderrefund:info:id")
    public BaseResponse info(@PathVariable Long id) {
        OrderRefund result = orderRefundService.selectByPrimaryKey(id);
        if (null == result){
            return BaseResponse.failed();
        }
        Order order = orderService.selectByPrimaryKey(result.getOrderId());
        if (order == null){
            return BaseResponse.failed("订单不存在");
        }
        OrderRefundVo orderRefundVo = new OrderRefundVo();
        BeanUtils.copyProperties(order,orderRefundVo);
        BeanUtils.copyProperties(result,orderRefundVo);
        List<OrderRefundItem> orderRefundItems = orderRefundItemService.selectOrderRefundItemListbByRefundId(id);
        orderRefundVo.setOrderRefundItemList(orderRefundItems);
        OrderRefundInfoResponse res = new OrderRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderRefundVo,id);
        return res;
    }

    @ApiOperation("订单ID查询退款详情")
    @GetMapping("/orderDetail/{orderId}")
    public BaseResponse orderRefundDetail(@PathVariable Long orderId){
        OrderRefundVo orderRefundVo = orderRefundService.selectByOrderId(orderId);
        if (orderRefundVo == null){
            return BaseResponse.failed("订单不存在处理中的退款请求");
        }
        List<OrderRefundItem> orderRefundItems = orderRefundItemService.selectOrderRefundItemListbByRefundId(orderRefundVo.getId());
        orderRefundVo.setOrderRefundItemList(orderRefundItems);
        OrderRefundInfoResponse res = new OrderRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderRefundVo,orderId);
        return res;
    }


    @ApiOperation("订单批量申请退款")
    @PostMapping("/batchApply")
    public BaseResponse batchApplyRefund(@RequestBody OrderBatchApplyRefundRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderRefundService.orderBatchApplyRefund(request,session);
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefund:list")
    public OrderRefundListResponse list(@RequestBody @Valid OrderRefundListRequest request) {
        List<OrderRefund> results = orderRefundService.select(request);
        Long total = orderRefundService.count(request);
        request.setTotal(total);
        OrderRefundListResponse res = new OrderRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


    @ApiOperation("订单申请退款")
    @PostMapping("/apply")
    public BaseResponse orderApplyRefund(@RequestBody ApplyOrderRefundRequest request){
        String key= RedisUtil.KEY_ORDER_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return orderRefundService.appApplyRefund(request,session);
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    //==============================admin===========================

    /**
     * 退款申请列表
     * @param request
     * @return
     */
    @ApiOperation("退款申请列表")
    @RequestMapping(value="/applyList", method=RequestMethod.POST)
    public OrderRefundListResponse refundOrderList(@RequestBody @Valid OrderRefundListRequest request) {
        return orderRefundService.refundOrderList(request);
    }

    /**
     * 普通订单申请退款
     * @param request
     * @return
     */
    @ApiOperation("普通订单申请退款,admin操作")
    @RequestMapping(value = "/applyRefund", method=RequestMethod.POST)
    public BaseResponse applyRefundOrder(@RequestBody ApplyOrderRefundRequest request) {
        String key= RedisUtil.KEY_ORDER_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            OrderApplyRefundResponse response = orderRefundService.applyRefund(request,session);
            if (response.getCode() == ResultCode.SUCCESS_CODE
            && request.isDirectRefund()){
                OrderRefund orderRefund = (OrderRefund) response.getData();
                ConfirmRefundRequest confirmRefundRequest = new ConfirmRefundRequest();
                confirmRefundRequest.setId(orderRefund.getId());
                confirmRefundRequest.setRefundItems(request.getRefundItemList());
                confirmRefundRequest.setRefundShipPrice(request.getShipPrice());
                confirmRefundRequest.setRefundVatAmount(request.getVatAmount());
                confirmRefundRequest.setServiceFee(request.getServiceFee());
                confirmRefundRequest.setActualRefundAmount(orderRefund.getRefundAmount());
                return orderRefundService.confirmRefund(confirmRefundRequest,session);
            }
            return response;
        } catch (CustomerException e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 退款申请更新退款备注
     * @param request
     * @return
     */
    @ApiOperation("退款申请更新退款备注")
    @RequestMapping(value = "/updateRemark",method = RequestMethod.POST)
    public BaseResponse updateRemark(@RequestBody @Valid OrderRefundUpdateRemarkRequest request) {
        return orderRefundService.updateRemark(request);
    }

    /**
     *驳回退款  填写驳回理由
     */
    @ApiOperation("退款申请更新退款备注")
    @RequestMapping(value = "/reject",method = RequestMethod.POST)
    public BaseResponse rejectRefund(@RequestBody @Valid OrderRefundRejectRefundRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return orderRefundService.rejectRefund(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }
    }

    /**
     * 确认退款
     */
    @ApiOperation("确认退款")
    @RequestMapping(value = "/confirm",method = RequestMethod.POST)
    public BaseResponse confirmRefund(@RequestBody @Valid ConfirmRefundRequest request){
        String key= RedisUtil.KEY_ORDER_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*5*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            BaseResponse response = orderRefundService.confirmRefund(request,session);
            return response;
        } catch (CustomerException e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 历史退款列表
     * @param request
     * @return
     */
    @ApiOperation("历史退款列表")
    @RequestMapping(value="/history", method=RequestMethod.POST)
    public OrderRefundListResponse refundOrderHistory(@RequestBody @Valid OrderRefundListRequest request) {
        return orderRefundService.refundOrderHistory(request);
    }


//    @PostMapping("/cancelSaihe")
//    public BaseResponse cancelSaihe(Long id,String clientOrderCode){
//        try {
//            orderRefundService.cancelSaiheOrderAndSynState(id,clientOrderCode);
//        } catch (CustomerException e) {
//            e.printStackTrace();
//            return BaseResponse.failed(e.getMessage());
//        }
//        return BaseResponse.success();
//    }

}
