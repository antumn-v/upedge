package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.request.ConfirmRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundRejectRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundUpdateRemarkRequest;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import com.upedge.oms.modules.wholesale.request.ApplyWholesaleOrderRefundRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundAddRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundUpdateRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundAddResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundInfoResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundListResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundUpdateResponse;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@Slf4j
@RestController
@RequestMapping("/wholesaleRefund")
public class WholesaleRefundController {
    @Autowired
    private WholesaleRefundService wholesaleRefundService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("申请退款")
    @PostMapping(value = "/apply")
    @ResponseBody
    public BaseResponse orderApplyRefund(@RequestBody ApplyWholesaleOrderRefundRequest request) {
        String key= RedisUtil.KEY_WHOLESALE_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return BaseResponse.failed();
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return wholesaleRefundService.orderApplyRefund(request,session);
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesalerefund:info:id")
    public WholesaleRefundInfoResponse info(@PathVariable Long id) {
        WholesaleRefund result = wholesaleRefundService.selectByPrimaryKey(id);
        WholesaleRefundInfoResponse res = new WholesaleRefundInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefund:list")
    public WholesaleRefundListResponse list(@RequestBody @Valid WholesaleRefundListRequest request) {
        List<WholesaleRefund> results = wholesaleRefundService.select(request);
        Long total = wholesaleRefundService.count(request);
        request.setTotal(total);
        WholesaleRefundListResponse res = new WholesaleRefundListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefund:add")
    public WholesaleRefundAddResponse add(@RequestBody @Valid WholesaleRefundAddRequest request) {
        WholesaleRefund entity=request.toWholesaleRefund();
        wholesaleRefundService.insertSelective(entity);
        WholesaleRefundAddResponse res = new WholesaleRefundAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesalerefund:update")
    public WholesaleRefundUpdateResponse update(@PathVariable Long id, @RequestBody @Valid WholesaleRefundUpdateRequest request) {
        WholesaleRefund entity=request.toWholesaleRefund(id);
        wholesaleRefundService.updateByPrimaryKeySelective(entity);
        WholesaleRefundUpdateResponse res = new WholesaleRefundUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    //========================admin=========================

    /**
     * 批发订单申请退款
     */
    @RequestMapping(value = "/admin/applyRefund", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse applyWholesaleOrder(@RequestBody @Valid ApplyWholesaleOrderRefundRequest request) {
        String key= RedisUtil.KEY_WHOLESALE_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return wholesaleRefundService.applyWholesaleOrder(request,session);
        } catch (CustomerException e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 批发订单退款申请列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/refundOrderList", method=RequestMethod.POST)
    public WholesaleRefundListResponse refundOrderList(@RequestBody @Valid WholesaleRefundListRequest request) {
        return wholesaleRefundService.refundOrderList(request);
    }

    /**
     *驳回退款  填写驳回理由
     */
    @RequestMapping(value = "/admin/rejectRefund",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse rejectRefund(@RequestBody @Valid OrderRefundRejectRefundRequest request){
        String key= RedisUtil.KEY_WHOLESALE_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*5*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return wholesaleRefundService.rejectRefund(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 批发订单历史退款列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/refundOrderHistory", method=RequestMethod.POST)
    public WholesaleRefundListResponse refundOrderHistory(@RequestBody @Valid WholesaleRefundListRequest request) {
        return wholesaleRefundService.refundOrderHistory(request);
    }

    /**
     * 退款申请更新退款备注
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/updateRemark",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateRemark(@RequestBody @Valid OrderRefundUpdateRemarkRequest request) {
        return wholesaleRefundService.updateRemark(request);
    }

    /**
     * 批发订单确认退款
     */
    @RequestMapping(value = "/admin/confirmRefund",method = RequestMethod.POST)
    public BaseResponse confirmRefund(@RequestBody @Valid ConfirmRefundRequest request){
        String key= RedisUtil.KEY_WHOLESALE_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*5*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return wholesaleRefundService.confirmRefund(request,session);
        } catch (CustomerException e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }
}
