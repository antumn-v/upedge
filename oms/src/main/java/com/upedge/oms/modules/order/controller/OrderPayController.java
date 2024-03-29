package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.async.OrderAsyncTask;
import com.upedge.oms.constant.StockOrderState;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import com.upedge.oms.modules.order.request.OrderPayRequest;
import com.upedge.oms.modules.order.response.OrderPayResponse;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "普通订单支付")
@RestController
@RequestMapping("/order/pay")
public class OrderPayController {

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    OrderAsyncTask orderAsyncTask;

    @Autowired
    StockOrderService stockOrderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    WholesaleOrderPayService wholesaleOrderPayService;

    @Autowired
    WholesaleOrderItemService wholesaleOrderItemService;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/list")
    public BaseResponse orderPayList(@RequestBody List<Long> orderIds) {
        String warehouseCode = RequestUtil.getWarehouseCode();

        Session session = UserUtil.getSession(redisTemplate);
        try {
            Long paymentId = IdGenerate.nextId();
            orderPayService.updatePaymentIdByIds(orderIds, paymentId, session.getCustomerId());
            return orderPayService.orderPayList(paymentId, session,warehouseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,new ArrayList<>());
    }

    @ApiOperation("余额支付订单")
    @PostMapping("/balance")
    public BaseResponse payOrderByBalance(@RequestBody @Valid OrderPayRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        String customerPayOrderLockKey = RedisKey.CUSTOMER_PAY_ORDER_LOCK + session.getCustomerId();
        boolean b = RedisUtil.lock(redisTemplate,customerPayOrderLockKey,10L,10 * 1000L);
        if (!b){
            return BaseResponse.failed("There is a transaction in progress");
        }
        List<Long> orderIds = request.getOrderIds();
        for (Long id : orderIds) {
            String key = RedisKey.STRING_ORDER_ID_PENDING + id;
            if (null != redisTemplate.opsForValue().get(key)) {
                RedisUtil.unLock(redisTemplate,customerPayOrderLockKey);
                return new OrderPayResponse(ResultCode.FAIL_CODE,"Orders cannot be submitted repeatedly within 3 seconds");
            } else {
                redisTemplate.opsForValue().set(key, System.currentTimeMillis(), 3, TimeUnit.SECONDS);
            }
        }
        BigDecimal amount = request.getAmount();
        Long paymentId = IdGenerate.nextId();
        int i = orderPayService.updatePaymentIdByIds(orderIds, paymentId, session.getCustomerId());
        if (i == orderIds.size()) {
            //库存检查
            List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemService.selectDischargeQuantityByPaymentId(paymentId);
            BaseResponse response = orderPayService.orderPayCheck(paymentId, amount, dischargeQuantityVos, session.getCustomerId(),"balance");
            if (response.getCode() == ResultCode.SUCCESS_CODE) {
                    orderPayService.payOrderByBalance(session, amount, paymentId, dischargeQuantityVos);
                    orderPayService.payOrderAsync(session.getCustomerId(),paymentId);
                    OrderPayResponse.PayResponse payResponse = new OrderPayResponse.PayResponse(paymentId,amount, TransactionConstant.PayMethod.ACCOUNT.getCode(),new Date());
                    RedisUtil.unLock(redisTemplate,customerPayOrderLockKey);
                    return new OrderPayResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,payResponse);
            }
            RedisUtil.unLock(redisTemplate,customerPayOrderLockKey);
            orderPayService.orderPayRollback(paymentId, session.getCustomerId(), dischargeQuantityVos);
            return response;
        }
        RedisUtil.unLock(redisTemplate,customerPayOrderLockKey);
        return new OrderPayResponse(ResultCode.FAIL_CODE,"Order error!");
    }

    @PostMapping("/paypal")
    public BaseResponse payOrderByPaypal(@RequestBody @Valid OrderPayRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        List<Long> orderIds = request.getOrderIds();
        for (Long id : orderIds) {
            String key = RedisKey.STRING_ORDER_ID_PENDING + id;
            if (null != redisTemplate.opsForValue().get(key)) {
                return BaseResponse.failed("Orders cannot be submitted repeatedly within 15 seconds");
            } else {
                redisTemplate.opsForValue().set(key, System.currentTimeMillis(), 15, TimeUnit.SECONDS);
            }
        }
        Long paymentId = IdGenerate.nextId();
        int i = orderPayService.updatePaymentIdByIds(orderIds, paymentId, session.getCustomerId());
        if (i > 0) {
            List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemService.selectDischargeQuantityByPaymentId(paymentId);

            BaseResponse response = orderPayService.orderPayCheck(paymentId, request.getAmount(), dischargeQuantityVos, session.getCustomerId(),"paypal");
            if (response.getCode() == ResultCode.SUCCESS_CODE) {
                response =  orderPayService.createPaypalOrder(session, request.getAmount(), paymentId, dischargeQuantityVos);
                if(response.getCode() == ResultCode.SUCCESS_CODE){
                    return response;
                }
            }
            orderPayService.orderPayRollback(paymentId, session.getCustomerId(), dischargeQuantityVos);
            return response;
        }
        return BaseResponse.failed();

    }

    @GetMapping("/paypal/execute")
    public OrderPayResponse paypalSuccess(String paymentId, String PayerID, @RequestParam("token") String token) {
        String tokenKey = RedisKey.HASH_PAYPAL_TOKEN + token;
        PaypalOrder order = (PaypalOrder) redisTemplate.opsForHash().get(tokenKey, "order");
        if (null == order) {
            return new OrderPayResponse(ResultCode.FAIL_CODE,"Transaction canceled or timed out.");
        }
        redisTemplate.delete(tokenKey);
        Session session = order.getSession();
        List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemService.selectDischargeQuantityByPaymentId(order.getId());
        if (StringUtils.isBlank(paymentId) || StringUtils.isBlank(PayerID) ) {
            orderPayService.orderPayRollback(order.getId(), session.getCustomerId(), dischargeQuantityVos);
            return new OrderPayResponse(ResultCode.FAIL_CODE,"Transaction canceled or timed out.");
        }

        String key = RedisKey.STRING_ORDER_PAY + paymentId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 10 * 1000L);
        if (!flag) {
            return new OrderPayResponse(ResultCode.FAIL_CODE,"Transaction canceled or timed out.");
        }

        BaseResponse response = new BaseResponse();

        if (StringUtils.isNotBlank(paymentId) && StringUtils.isNotBlank(PayerID)) {

            PaypalExecuteRequest paypalExecuteRequest = new PaypalExecuteRequest(paymentId, PayerID, token, order);
            response = umsFeignClient.executePaypalOrder(paypalExecuteRequest);
            String state = "pending";
            if (response.getCode() == ResultCode.SUCCESS_CODE) {
                LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) response.getData();
                state = linkedHashMap.get("state");
                if (state.equals("completed")) {
                    orderPayService.payOrderByPaypal(session.getId(), session.getCustomerId(), order.getId());
                }
                RedisUtil.unLock(redisTemplate, key);
                OrderPayCheckResultVo  orderPayCheckResultVo = creatOrderPayCheckResultVo(order);
                OrderPayResponse.PayResponse payResponse = new OrderPayResponse.PayResponse(order.getId(),order.getAmount(), TransactionConstant.PayMethod.PAYPAL.getCode(),new Date(),orderPayCheckResultVo.getTradingDataVo());
                return new OrderPayResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,payResponse);
            }
        } else {
            RedisUtil.unLock(redisTemplate, key);
            orderPayService.orderPayRollback(order.getId(), session.getCustomerId(), dischargeQuantityVos);
        }
        return new OrderPayResponse(ResultCode.FAIL_CODE,"Transaction canceled or timed out.");

    }

    @PostMapping("/paypal/completed")
    public BaseResponse orderPaypalCompleted(@RequestBody PaypalPayment paypalPayment) {
        switch (paypalPayment.getState()) {
            case "pending":
                break;
            case "completed":
                orderPayService.payOrderByPaypal(paypalPayment.getUserId(),paypalPayment.getCustomerId(),paypalPayment.getId());
                break;
            default:
                List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemService.selectDischargeQuantityByPaymentId(paypalPayment.getId());
                orderPayService.orderPayRollback(paypalPayment.getId(),paypalPayment.getCustomerId(),dischargeQuantityVos);
                break;
        }
        return BaseResponse.success();
    }

    @PostMapping("/rollback")
    public BaseResponse orderByRollback(@RequestBody PaypalOrder paypalOrder) {
        switch (paypalOrder.getOrderType()) {
            case OrderType.WHOLESALE:
                List<ItemDischargeQuantityVo> itemDischargeQuantityVos = wholesaleOrderItemService.selectDischargeQuantityByPaymentId(paypalOrder.getId());
                wholesaleOrderPayService.orderPayRollBack(paypalOrder.getId(), paypalOrder.getSession().getCustomerId(), itemDischargeQuantityVos);
                break;
            case OrderType.NORMAL:
                List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemService.selectDischargeQuantityByPaymentId(paypalOrder.getId());
                orderPayService.orderPayRollback(paypalOrder.getId(), paypalOrder.getSession().getCustomerId(), dischargeQuantityVos);
                break;
            case OrderType.STOCK:
                stockOrderService.updatePayStateByPaymentId(paypalOrder.getId(), StockOrderState.UN_PAID);
                break;
            default:
                break;
        }
        return BaseResponse.success();
    }

    private OrderPayCheckResultVo creatOrderPayCheckResultVo(PaypalOrder order) {
        OrderPayCheckResultVo orderPayCheckResultVo = new OrderPayCheckResultVo();
        orderPayCheckResultVo.getTradingDataVo().setTransactionId(order.getId().toString());
        orderPayCheckResultVo.getTradingDataVo().setTransactionAffiliation(order.getSession().getLoginname());
        orderPayCheckResultVo.getTradingDataVo().setTransactionTotal(order.getProductAmount().add(order.getShipPrice()));
        orderPayCheckResultVo.getTradingDataVo().setTransactionShipping(order.getShipPrice());

        if (!CollectionUtils.isEmpty(order.getItems())){
            for (PaypalOrder.PaypalOrderItem item : order.getItems()) {
                OrderPayCheckResultVo.TransactionProduct transactionProduct = new OrderPayCheckResultVo.TransactionProduct();
                transactionProduct.setName(item.getName());
                transactionProduct.setSku(item.getSku());
                transactionProduct.setPrice(item.getPrice());
                transactionProduct.setQuantity(item.getQuantity());
                orderPayCheckResultVo.getTradingDataVo().getTransactionProducts().add(transactionProduct);
            }
        }
        return orderPayCheckResultVo;
    }
}
