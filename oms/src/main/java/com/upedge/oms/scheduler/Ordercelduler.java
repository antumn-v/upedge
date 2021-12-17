package com.upedge.oms.scheduler;

import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.statistics.service.OrderDailyRefundCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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



}
