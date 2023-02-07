package com.upedge.oms.init;

import com.upedge.oms.modules.order.service.OrderReshipInfoService;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.vat.service.VatRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

@Slf4j
//@Component
public class OmsRedisInit {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    OrderReshipInfoService orderReshipInfoService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @PostConstruct
    public void packageCurrentUsdRateInit(){
//        Set<String> keys = redisTemplate.keys(RedisKey.STRING_AREA_VAT_RULE + "*");
//        redisTemplate.delete(keys);

        customerProductStockService.redisInit();

        orderReshipInfoService.redisInitReshipOrderApplication();
        log.warn("redis客户商品库存加载完毕----------------------------");
    }
}
