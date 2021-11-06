package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.product.VariantQuantity;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.constant.StockOrderState;
import com.upedge.oms.enums.StockOrderTagEnum;
import com.upedge.oms.modules.stock.dto.StockOrderListDto;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.request.StockOrderListRequest;
import com.upedge.oms.modules.stock.response.StockOrderInfoResponse;
import com.upedge.oms.modules.stock.response.StockOrderListResponse;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.stock.vo.StockOrderVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author author
 */
@Slf4j
@RestController
@RequestMapping("/stock/order")
public class StockOrderController {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StockOrderService stockOrderService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    UmsFeignClient umsFeignClient;


    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @Permission(permission = "stock:order:info:id")
    public StockOrderInfoResponse info(@PathVariable Long id) {
        StockOrderVo result = stockOrderService.selectOrderById(id);
        StockOrderInfoResponse res = new StockOrderInfoResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result, id);
        return res;
    }

    @ApiOperation("创建备库订单")
    @PostMapping("/create")
    public BaseResponse excelImportStockOrder(@RequestBody List<VariantQuantity> variantQuantities) {
        Session session = UserUtil.getSession(redisTemplate);
        return stockOrderService.excelImportStockOrders(session, variantQuantities);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @Permission(permission = "stock:order:list")
    public StockOrderListResponse list(@RequestBody @Valid StockOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()) {
            request.setT(new StockOrderListDto());
        }

        request.initFromNum();

        StockOrderListDto stockOrderListDto = request.getT();

        stockOrderListDto.setCustomerId(session.getCustomerId());

        StockOrderTagEnum enums = StockOrderTagEnum.valueOf(request.getTag());
        stockOrderListDto.setPayState(enums.getPayState());
        stockOrderListDto.setRefundState(enums.getRefundState());

        List<StockOrderVo> results = stockOrderService.selectOrderList(request);
        Long total = stockOrderService.countOrderList(request);
        request.setTotal(total);
        StockOrderListResponse res = new StockOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }

    @PostMapping("/count")
    public StockOrderListResponse count(@RequestBody @Valid StockOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        Map<String, Long> map = new HashMap<>();
        if (null == request.getT()) {
            request.setT(new StockOrderListDto());
        }
        for (StockOrderTagEnum tag: StockOrderTagEnum.values()) {
            StockOrderListDto stockOrderListDto = request.getT();

            stockOrderListDto.setCustomerId(session.getCustomerId());

            StockOrderTagEnum enums = StockOrderTagEnum.valueOf(tag.name());
            stockOrderListDto.setPayState(enums.getPayState());
            stockOrderListDto.setRefundState(enums.getRefundState());

            Long total = stockOrderService.countOrderList(request);
            map.put(tag.name(),total);
        }
        StockOrderListResponse res = new StockOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map, request);
        return res;
    }

    @PostMapping("/{orderId}/cancel")
    public BaseResponse cancelOrder(@PathVariable Long orderId) {
        StockOrder order = new StockOrder();
        order.setId(orderId);
        order.setPayState(StockOrderState.CANCELED);
        stockOrderService.updateOrderPayState(order);
        return BaseResponse.success();
    }

    @PostMapping("/{orderId}/restore")
    public BaseResponse restoreOrder(@PathVariable Long orderId) {
        StockOrder order = new StockOrder();
        order.setId(orderId);
        order.setPayState(StockOrderState.UN_PAID);
        stockOrderService.updateOrderPayState(order);
        return BaseResponse.success();
    }

    @PostMapping("/pay/list")
    public BaseResponse stockOrderPayList(@RequestBody List<Long> orderIds) throws InterruptedException {
        List<StockOrderVo> stockOrderVos = new ArrayList<>();
        orderIds.forEach(orderId -> {
            boolean b = stockOrderService.refreshOrderDetail(orderId);
            if (b) {
                stockOrderVos.add(stockOrderService.selectOrderById(orderId));
            }
        });

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, stockOrderVos);
    }

    @PostMapping("/pay/paypal")
    public BaseResponse payByPaypal(@RequestBody List<Long> orderIds) {

        Session session = UserUtil.getSession(redisTemplate);

        Iterator<Long> ids = orderIds.iterator();
        while (ids.hasNext()) {
            Long id = ids.next();
            String key = RedisKey.STRING_ORDER_ID_PENDING + id;
            if (redisTemplate.hasKey(key)) {
                return BaseResponse.failed("The orders are processing.Please try again 15 seconds later.");
            } else {
                redisTemplate.opsForValue().set(key, System.currentTimeMillis(), 15, TimeUnit.SECONDS);
            }

            boolean b = stockOrderService.refreshOrderDetail(id);
            if (!b) {
                ids.remove();
            } else {
                redisTemplate.opsForValue().set(String.valueOf(id), System.currentTimeMillis(), 20, TimeUnit.SECONDS);
            }
        }
        if (ListUtils.isNotEmpty(orderIds)) {
            return stockOrderService.createPaypalOrder(orderIds, session);
        }
        return BaseResponse.failed();
    }

    @PostMapping("/pay/balance")
    public BaseResponse payByBalance(@RequestBody List<Long> orderIds) {
        Session session = UserUtil.getSession(redisTemplate);
        Iterator<Long> ids = orderIds.iterator();
        while (ids.hasNext()) {
            Long id = ids.next();
            String key = RedisKey.STRING_ORDER_ID_PENDING + id;
            if (redisTemplate.hasKey(key)) {
                return BaseResponse.failed("The orders are processing.Please try again 15 seconds later.");
            }

            boolean b = stockOrderService.refreshOrderDetail(id);
            if (!b) {
                ids.remove();
            } else {
                redisTemplate.opsForValue().set(key, System.currentTimeMillis(), 15, TimeUnit.SECONDS);
            }
        }
        if (ListUtils.isNotEmpty(orderIds)) {
            PaymentDetail detail = stockOrderService.payOrderByBalance(orderIds, session);
            stockOrderService.payOrderAsync(detail);
            if (null != detail) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    stockOrderService.sendSavePaymentDetailMessage(detail);
                });
                return BaseResponse.success();
            }
        }
        return BaseResponse.failed();
    }

    @GetMapping("/pay/paypal/execute")
    public BaseResponse paypalSuccess(String paymentId, String PayerID, @RequestParam("token") String token) throws IOException {
        String tokenKey = RedisKey.HASH_PAYPAL_TOKEN + token;
        PaypalOrder order = (PaypalOrder) redisTemplate.opsForHash().get(tokenKey, "order");
        redisTemplate.delete(tokenKey);
        if(null == order){
            return BaseResponse.failed("Transaction canceled or timed out.");
        }
        if (StringUtils.isBlank(paymentId) || StringUtils.isBlank(PayerID) ) {
            stockOrderService.updatePayStateByPaymentId(order.getId(), StockOrderState.UN_PAID);
            return BaseResponse.failed("Transaction canceled or timed out.");
        } else {

            String key = RedisKey.STRING_ORDER_PAY + paymentId;
            boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 10 * 1000L);
            if (!flag) {
                return BaseResponse.failed();
            }            PaypalExecuteRequest paypalExecuteRequest = new PaypalExecuteRequest(paymentId, PayerID, token, order);
            BaseResponse response = umsFeignClient.executePaypalOrder(paypalExecuteRequest);
            if (response.getCode() == ResultCode.SUCCESS_CODE) {
                LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) response.getData();
                String state = linkedHashMap.get("state");
                if (state.equals("completed")) {
                    PaymentDetail paymentDetail = stockOrderService.orderPaidByPaypal(order.getId(), order.getSession().getCustomerId(), order.getSession().getId());
                    stockOrderService.payOrderAsync(paymentDetail);
                }
                response = new BaseResponse();
                Map<String, Object> map = new HashMap<>();
                map.put("payAmount", order.getAmount());
                response.setData(map);
                response.setCode(ResultCode.SUCCESS_CODE);
                RedisUtil.unLock(redisTemplate,key);
                return response;
            } else {
                stockOrderService.updatePayStateByPaymentId(order.getId(), StockOrderState.UN_PAID);
            }
            RedisUtil.unLock(redisTemplate,key);
            return BaseResponse.failed();
        }


    }

    @GetMapping("/pay/paypal/cancel")
    public void paypalCancel(@RequestParam("token") String token) {
        PaypalOrder order = (PaypalOrder) redisTemplate.opsForHash().get(token, "order");
        if (null != order) {
            String key = order.getSession().getCustomerId() + ":paypal:pending:" + order.getOrderType();
            stockOrderService.updatePayStateByPaymentId(order.getId(), StockOrderState.UN_PAID);
            redisTemplate.delete(token);
            redisTemplate.opsForList().remove(key, 1, token);
        }
    }

    @PostMapping("/pay/paypal/completed")
    public BaseResponse stockPaypalCompleted(@RequestBody @Valid PaypalPayment paypalPayment) {
        switch (paypalPayment.getState()) {
            case "pending":
                break;
            case "completed":
                PaymentDetail paymentDetail = stockOrderService.orderPaidByPaypal(paypalPayment.getId(), paypalPayment.getCustomerId(), paypalPayment.getUserId());
                stockOrderService.payOrderAsync(paymentDetail);
                break;
            default:
                stockOrderService.updatePayStateByPaymentId(paypalPayment.getId(), StockOrderState.UN_PAID);
                break;
        }
        return BaseResponse.success();
    }

    public static void main(String[] args) {

    }

}
