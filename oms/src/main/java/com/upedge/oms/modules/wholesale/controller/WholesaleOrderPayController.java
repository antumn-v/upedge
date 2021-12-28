package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
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
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.async.OrderAsyncTask;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import com.upedge.oms.modules.order.request.OrderPayRequest;
import com.upedge.oms.modules.order.response.OrderPayResponse;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderPayService;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(tags = "批发订单支付")
@RestController
@RequestMapping("/wholesaleOrder/pay")
public class WholesaleOrderPayController {

    @Autowired
    WholesaleOrderPayService wholesaleOrderPayService;

    @Autowired
    WholesaleOrderItemService wholesaleOrderItemService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OrderAsyncTask orderAsyncTask;

    @Autowired
    UmsFeignClient umsFeignClient;

//    @ApiOperation("支付列表")
//    @PostMapping("/list")
    public BaseResponse wholesaleOrderPayList(@RequestBody List<Long> ids) {
        Session session = UserUtil.getSession(redisTemplate);

        List<WholesaleOrderAppVo> appVos = wholesaleOrderPayService.orderPayList(ids, session);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appVos);
    }

    @ApiOperation("余额支付订单")
    @PostMapping("/balance")
    public OrderPayResponse payOrderByBalance(@RequestBody @Valid OrderPayRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Long paymentId = IdGenerate.nextId();
        List<Long> ids = request.getOrderIds();
        for (Long id : ids) {
            String key = RedisKey.STRING_ORDER_ID_PENDING + id;
            if (null != redisTemplate.opsForValue().get(key)) {
                return new OrderPayResponse(ResultCode.FAIL_CODE,"Orders cannot be submitted repeatedly within 15 seconds");
            } else {
                redisTemplate.opsForValue().set(key, System.currentTimeMillis(), 15, TimeUnit.SECONDS);
            }
        }
        int i = wholesaleOrderPayService.updatePaymentIdByIds(paymentId, ids);
        if (i > 0) {
            List<ItemDischargeQuantityVo> dischargeQuantityVos = wholesaleOrderItemService.selectDischargeQuantityByPaymentId(paymentId);
            OrderPayCheckResultVo orderPayCheckResultVo = wholesaleOrderPayService.orderPayCheck(paymentId, request.getAmount(), dischargeQuantityVos, session.getCustomerId(),"balance");
            if (orderPayCheckResultVo.getPayMessage().equals("success")) {
                Date payTime = new Date();
                String result = wholesaleOrderPayService.payOrderByBalance(paymentId, request.getAmount(), session, payTime);
                if (result.equals("success")) {
//                    wholesaleOrderPayService.processAfterPaid(paymentId,session.getCustomerId(),session.getId(),payTime);
                    OrderPayResponse.PayResponse payResponse = new OrderPayResponse.PayResponse(paymentId,request.getAmount(), TransactionConstant.PayMethod.ACCOUNT.getCode(),payTime,orderPayCheckResultVo.getTradingDataVo());
                    return new OrderPayResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,payResponse);
                }
            }
            wholesaleOrderPayService.orderPayRollBack(paymentId, session.getCustomerId(), dischargeQuantityVos);
            return new OrderPayResponse(ResultCode.FAIL_CODE,orderPayCheckResultVo.getPayMessage());
        }
        return new OrderPayResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
    }

//    @ApiOperation("paypal支付订单")
//    @PostMapping("/paypal")
    public BaseResponse paypalOrderByPaypal(@RequestBody @Valid OrderPayRequest request) {
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
        int i = wholesaleOrderPayService.updatePaymentIdByIds(paymentId, orderIds);
        if (i > 0) {
            List<ItemDischargeQuantityVo> dischargeQuantityVos = wholesaleOrderItemService.selectDischargeQuantityByPaymentId(paymentId);
            OrderPayCheckResultVo orderPayCheckResultVo = wholesaleOrderPayService.orderPayCheck(paymentId, request.getAmount(), dischargeQuantityVos, session.getCustomerId(),"paypal");
            if (orderPayCheckResultVo.getPayMessage().equals("success")) {
                try {
                    BaseResponse response = wholesaleOrderPayService.createPaypalOrder(session, request.getAmount(), paymentId);
                    if (response.getCode() == ResultCode.SUCCESS_CODE) {
                        return response;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            wholesaleOrderPayService.orderPayRollBack(paymentId, session.getCustomerId(), dischargeQuantityVos);

        }
        return BaseResponse.failed();
    }

//    @ApiOperation("paypal验证")
//    @GetMapping("/paypal/execute")
    public BaseResponse paypalSuccess(String paymentId, String PayerID, @RequestParam("token") String token) {
        String tokenKey = RedisKey.HASH_PAYPAL_TOKEN + token;
        PaypalOrder order = (PaypalOrder) redisTemplate.opsForHash().get(tokenKey, "order");
        if (null == order) {
            return BaseResponse.failed("Transaction canceled or timed out.");
        }
        if (StringUtils.isBlank(paymentId) || StringUtils.isBlank(PayerID) ) {
            wholesaleOrderPayService.updatePayStateByPaymentId(order.getId(), 0);
            return BaseResponse.failed("Transaction canceled or timed out.");
        }
        String key = RedisKey.STRING_ORDER_PAY + paymentId;

        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 10 * 1000L);
        if (!flag) {
            return BaseResponse.failed();
        }
        Session session = order.getSession();
        BaseResponse response = new BaseResponse();

        PaypalExecuteRequest paypalExecuteRequest = new PaypalExecuteRequest(paymentId, PayerID, token, order);
        response = umsFeignClient.executePaypalOrder(paypalExecuteRequest);
        String state = "pending";
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) response.getData();
            state = linkedHashMap.get("state");
            if (state.equals("completed")) {
                wholesaleOrderPayService.payOrderByPaypal(session.getId(), session.getCustomerId(), order.getId());
//                wholesaleOrderPayService.processAfterPaid(session.getId(), session.getCustomerId(), order.getId(),new Date());
            }
            redisTemplate.delete(tokenKey);
            RedisUtil.unLock(redisTemplate, key);

            Map<String, Object> map = new HashMap<>();
            map.put("payAmount", order.getAmount());
            OrderPayCheckResultVo  orderPayCheckResultVo = creatOrderPayCheckResultVo(order);
            map.put("tradingDataVo", orderPayCheckResultVo.getTradingDataVo());
            response.setData(map);
            response.setCode(ResultCode.SUCCESS_CODE);
            return response;

        } else {
            wholesaleOrderPayService.updatePayStateByPaymentId(order.getId(), 0);
        }
        RedisUtil.unLock(redisTemplate, key);
        redisTemplate.delete(tokenKey);
        return BaseResponse.failed();
    }

    /**
     * 处理paypal支付的批发订单
     * @param paypalPayment
     * @return
     */
    @PostMapping("/paypal/completed")
    public BaseResponse wholesalePaypalCompleted(@RequestBody @Valid PaypalPayment paypalPayment){
        switch (paypalPayment.getState()){
            case "pending":
                break;
            case "completed":
                wholesaleOrderPayService.payOrderByPaypal(paypalPayment.getUserId(),paypalPayment.getCustomerId(),paypalPayment.getId());
//                wholesaleOrderPayService.processAfterPaid(paypalPayment.getId(),paypalPayment.getCustomerId(),paypalPayment.getUserId(),new Date());
                break;
            default:
                List<ItemDischargeQuantityVo> dischargeQuantityVos = wholesaleOrderItemService.selectDischargeQuantityByPaymentId(paypalPayment.getId());
                wholesaleOrderPayService.orderPayRollBack(paypalPayment.getId(),paypalPayment.getCustomerId(),dischargeQuantityVos);
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
