package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.request.*;
import com.upedge.oms.modules.stock.response.ApplyStockOrderRefundResponse;
import com.upedge.oms.modules.stock.response.CustomerProductStockListResponse;
import com.upedge.oms.modules.stock.response.StockOrderListResponse;
import com.upedge.oms.modules.stock.response.StockOrderRefundListResponse;
import com.upedge.oms.modules.stock.service.AdminStockService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@Api(tags = "后台备库订单管理")
@RestController
@RequestMapping("/stock")
public class  AdminStockController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    AdminStockService adminStockService;
    @Autowired
    StockOrderService stockOrderService;

    /**
     * 备库订单列表
     * @param request
     * @return
     */
    @ApiOperation("备库订单列表")
    @RequestMapping(value="/orderList", method= RequestMethod.POST)
    public StockOrderListResponse stockList(@RequestBody @Valid AdminStockOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return adminStockService.stockList(request,session);
    }

    /**
     * 历史备库订单
     */
    @ApiOperation("历史备库订单")
    @RequestMapping(value="/historyStockOrder", method= RequestMethod.POST)
    public StockOrderListResponse historyStockOrder(@RequestBody @Valid AdminStockOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return adminStockService.historyStockOrder(request,session);
    }

    /**
     * 生成备库订单
     * @return
     */
    @ApiOperation("上传订单到赛盒")
    @RequestMapping(value = "/createProcurement", method= RequestMethod.POST)
    public BaseResponse createProcurement(@RequestBody @Valid CreateProcurementRequest request) {
        String key=RedisUtil.KEY_STOCK_CREATE_PROCUREMENT+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*3);
        //获取锁成功
        if(!flag){
            log.debug("获取锁失败:{}",key);
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        try {
            //生成备库订单 上传备库单到赛盒
            return adminStockService.createProcurement(request);
        }catch (Exception e){
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
            log.debug("释放锁:{}",key);
        }

    }

    @ApiOperation("同步赛盒SKU")
    @RequestMapping(value = "/refreshSaiheSku/{id}", method= RequestMethod.POST)
    public BaseResponse refreshSaiheSku(@PathVariable Long id) {

        StockOrder appStockOrder=stockOrderService.selectByPrimaryKey(id);
        if(appStockOrder==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"备库订单不存在!");
        }
        return adminStockService.refreshSaiheSku(id);
    }

    /**
     * 更新赛盒系统SKU
     * @param request
     * @return
     */
    @ApiOperation("更新赛盒系统SKU")
    @RequestMapping(value = "/updateSaiheSku", method= RequestMethod.POST)
    @ResponseBody
    public BaseResponse updateSaiheSku(@RequestBody @Valid UpdateSaiheSkuRequest request){
        return adminStockService.updateSaiheSku(request);
    }

    /**
     * 备库退款列表
     * @param request
     * @return
     */
    @ApiOperation("备库退款列表")
    @RequestMapping(value="/refundOrderList", method= RequestMethod.POST)
    public StockOrderRefundListResponse refundOrderList(@RequestBody @Valid StockOrderRefundListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return adminStockService.refundOrderList(request,session);
    }

    /**
     * 历史备库退款列表
     */
    @ApiOperation("历史备库退款列表")
    @RequestMapping(value="/historyRefundOrder", method= RequestMethod.POST)
    public StockOrderRefundListResponse historyRefundOrder(@RequestBody @Valid StockOrderRefundListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return adminStockService.historyRefundOrder(request,session);
    }

    @ApiOperation("备库订单申请退款")
    @RequestMapping(value = "/applyRefund", method= RequestMethod.POST)
    public ApplyStockOrderRefundResponse applyRefund(@RequestBody @Valid ApplyStockOrderRefundRequest request) {
        String key=RedisUtil.KEY_STOCK_APPLY_REFUND+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            log.debug("获取锁失败:{}",key);
            return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        log.debug("获取锁:{}",key);
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return adminStockService.applyRefund(request,session);
        } catch (CustomerException e) {
            return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
            log.debug("释放锁:{}",key);
        }
    }

    /**
     * 客户商品库存列表
     * @param request
     * @return
     */
    @ApiOperation("客户商品库存列表")
    @RequestMapping(value="/productStockList", method=RequestMethod.POST)
    public CustomerProductStockListResponse productStockList(@RequestBody @Valid CustomerProductStockListRequest request) {
        return adminStockService.productStockList(request);
    }

    /**
     * 库存记录列表
     * @param request
     * @return
     */
    @ApiOperation("库存记录列表")
    @RequestMapping(value="/stockRecordList", method=RequestMethod.POST)
    public BaseResponse stockRecordList(@RequestBody @Valid CustomerStockRecordListRequest request) {
        return adminStockService.stockRecordList(request);
    }

    /**
     * 确认退款
     * @param request
     * @return
     */
    @ApiOperation("确认退款")
    @RequestMapping(value = "/confirmRefund",method = RequestMethod.POST)
    public BaseResponse confirmRefund(@RequestBody @Valid ConfirmStockOrderRefundRequest request) {
        String key=RedisUtil.KEY_STOCK_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            log.debug("获取锁失败:{}",key);
            return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        log.debug("获取锁:{}",key);
        try {
            Session session = UserUtil.getSession(redisTemplate);
            return adminStockService.confirmRefund(request, session);
        }catch (Exception e){
            //e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
            log.debug("释放锁:{}",key);
        }
    }

    /**
     * 驳回申请
     * @param request
     * @return
     */
    @ApiOperation("驳回退款申请")
    @RequestMapping(value = "/rejectApply",method = RequestMethod.POST)
    public BaseResponse rejectApply(@RequestBody @Valid RejectApplyStockOrderRefundRequest request) {
        String key=RedisUtil.KEY_STOCK_PROCESS_REFUND+request.getId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            log.debug("获取锁失败:{}",key);
            return new ApplyStockOrderRefundResponse(ResultCode.FAIL_CODE,"操作中...");
        }
        log.debug("获取锁:{}",key);
        try {
            Session session = UserUtil.getSession(redisTemplate);
            return adminStockService.rejectApply(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
            log.debug("释放锁:{}",key);
        }
    }

}
