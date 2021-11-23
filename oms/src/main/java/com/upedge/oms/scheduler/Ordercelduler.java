package com.upedge.oms.scheduler;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.statistics.service.OrderDailyRefundCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
//@Component
public class Ordercelduler {

    @Autowired
    StoreOrderService storeOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDailyPayCountService orderDailyPayCountService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private OrderDailyRefundCountService orderDailyRefundCountService;


    /**
     * 定时器从redis中获取第三方平台推送过来的订单数据并处理
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void webhookShopifyOrder() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                long i = redisTemplate.opsForList().size(RedisKey.LIST_SHOPIFY_ORDER_WEBHOOK);
                if (i == 0) {
                    return;
                }
                log.warn("key:webhook:shopify:order长度：{}",i);
                for (int j = 0; j < i; j++) {
                    StoreApiRequest storeApiRequest = (StoreApiRequest) redisTemplate.opsForList().rightPop(RedisKey.LIST_SHOPIFY_ORDER_WEBHOOK);
                    log.warn("shopifyOrderWebhook:{}", storeApiRequest);
                    StoreOrder storeOrder = storeOrderService.shopifyOrderUpdate(storeApiRequest);
                    if (storeOrder != null) {
                        storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
                        orderService.createOrderByStoreOrder(storeOrder.getId());
                    }
                }
            }
        });

    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void webhookWoocommerceOrder() {


        Long i = redisTemplate.opsForList().size(RedisKey.LIST_WOOCOMMERCE_ORDER_WEBHOOK);
        if (i == 0) {
            return;
        }
        for (int j = 0; j < i; j++) {
            StoreApiRequest storeApiRequest = (StoreApiRequest) redisTemplate.opsForList().rightPop(RedisKey.LIST_WOOCOMMERCE_ORDER_WEBHOOK);
            StoreOrder storeOrder = storeOrderService.woocommerceOrderUpdate(storeApiRequest);
            if (null != storeOrder) {
                storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
                orderService.createOrderByStoreOrder(storeOrder.getId());
            }
        }


    }

    @Scheduled(cron = "*/5 * * * * ?")
    public void webhookShoplazzaOrder() {

        Long i = redisTemplate.opsForList().size(RedisKey.LIST_SHOPLAZZA_ORDER_WEBHOOK);
        if (i == 0) {
            return;
        }
        for (int j = 0; j < i; j++) {
            StoreApiRequest storeApiRequest = (StoreApiRequest) redisTemplate.opsForList().rightPop(RedisKey.LIST_SHOPLAZZA_ORDER_WEBHOOK);
            StoreOrder storeOrder = storeOrderService.shoplazzaOrderUpdate(storeApiRequest);
            if (null != storeOrder) {
                storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
                orderService.createOrderByStoreOrder(storeOrder.getId());
            }
        }


    }

    /**
     * 计算更新客户每日支付订单数据
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void orderDaliyCount() {
        Long i = redisTemplate.opsForList().size(RedisKey.LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE);
        if (i == 0) {
            return;
        }
        for (int j = 0; j < i; j++) {
            CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest =
                    (CustomerOrderDailyCountUpdateRequest) redisTemplate.opsForList().rightPop(RedisKey.LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE);
            Future<?> submit = threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    orderDailyPayCountService.updateCustomerOrderDailyCount(customerOrderDailyCountUpdateRequest);
                }
            });
            try {
                submit.get();
            } catch (Exception e) {
                log.error("计算更新客户每日支付订单数据:{}", customerOrderDailyCountUpdateRequest);
                redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE, customerOrderDailyCountUpdateRequest);
                e.printStackTrace();
            }
        }
    }


    /**
     * 计算更新客户每日退款订单数据
     */
    @Scheduled(cron = "0 */5 * * * ?")
    public void orderDaliyRefundCount() {
        Long i = redisTemplate.opsForList().size(RedisKey.LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE);
        if (i == 0) {
            return;
        }

        for (int j = 0; j < i; j++) {
            OrderRefundDailyCountRequest orderRefundDailyCountRequest =
                    (OrderRefundDailyCountRequest) redisTemplate.opsForList().rightPop(RedisKey.LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE);
            Future<?> submit = threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    orderDailyRefundCountService.OrderDailyRefundCount(orderRefundDailyCountRequest);
                }
            });
            try {
                submit.get();
            } catch (Exception e) {
                log.error("计算更新客户每日退款订单数据:{}", orderRefundDailyCountRequest);
                redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE, orderRefundDailyCountRequest);
                e.printStackTrace();
            }
        }
    }
}
