package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderRefundAddResponse;
import com.upedge.oms.modules.order.response.OrderRefundInfoResponse;
import com.upedge.oms.modules.order.response.OrderRefundListResponse;
import com.upedge.oms.modules.order.response.OrderRefundUpdateResponse;
import com.upedge.oms.modules.order.service.OrderRefundService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/orderRefund")
public class OrderRefundController {
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:orderrefund:info:id")
    public OrderRefundInfoResponse info(@PathVariable Long id) {
        OrderRefund result = orderRefundService.selectByPrimaryKey(id);
        OrderRefundInfoResponse res = new OrderRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
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

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefund:add")
    public OrderRefundAddResponse add(@RequestBody @Valid OrderRefundAddRequest request) {
        OrderRefund entity=request.toOrderRefund();
        orderRefundService.insertSelective(entity);
        OrderRefundAddResponse res = new OrderRefundAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:orderrefund:update")
    public OrderRefundUpdateResponse update(@PathVariable Long id, @RequestBody @Valid OrderRefundUpdateRequest request) {
        OrderRefund entity=request.toOrderRefund(id);
        orderRefundService.updateByPrimaryKeySelective(entity);
        OrderRefundUpdateResponse res = new OrderRefundUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("订单申请退款")
    @PostMapping("/refund/apply")
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
    @RequestMapping(value="/admin/refundOrderList", method=RequestMethod.POST)
    public OrderRefundListResponse refundOrderList(@RequestBody @Valid OrderRefundListRequest request) {
        return orderRefundService.refundOrderList(request);
    }

    /**
     * 普通订单申请退款
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/applyRefund", method=RequestMethod.POST)
    public BaseResponse applyRefundOrder(@RequestBody ApplyOrderRefundRequest request) {
        String key= RedisUtil.KEY_ORDER_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return orderRefundService.applyRefund(request,session);
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
    @RequestMapping(value = "/admin/updateRemark",method = RequestMethod.POST)
    public BaseResponse updateRemark(@RequestBody @Valid OrderRefundUpdateRemarkRequest request) {
        return orderRefundService.updateRemark(request);
    }

    /**
     *驳回退款  填写驳回理由
     */
    @RequestMapping(value = "/admin/rejectRefund",method = RequestMethod.POST)
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
    @RequestMapping(value = "/admin/confirmRefund",method = RequestMethod.POST)
    public BaseResponse confirmRefund(@RequestBody @Valid ConfirmRefundRequest request){
        String key= RedisUtil.KEY_ORDER_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*5*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return orderRefundService.confirmRefund(request,session);
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
    @RequestMapping(value="/admin/refundOrderHistory", method=RequestMethod.POST)
    public OrderRefundListResponse refundOrderHistory(@RequestBody @Valid OrderRefundListRequest request) {
        return orderRefundService.refundOrderHistory(request);
    }

}