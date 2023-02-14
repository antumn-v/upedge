package com.upedge.oms.init;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.oms.modules.common.entity.OrderErrorMessage;
import com.upedge.oms.modules.common.service.OrderErrorMessageService;
import com.upedge.oms.modules.order.service.OrderReshipInfoService;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.vat.service.VatRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController("/redis")
public class OmsRedisInit {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    OrderReshipInfoService orderReshipInfoService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    OrderErrorMessageService orderErrorMessageService;

    @PostMapping("/init")
    public void packageCurrentUsdRateInit(){
//        Set<String> keys = redisTemplate.keys(RedisKey.STRING_AREA_VAT_RULE + "*");
//        redisTemplate.delete(keys);

        List<OrderErrorMessage> orderErrorMessages = orderErrorMessageService.select(new Page<>());
        for (OrderErrorMessage orderErrorMessage : orderErrorMessages) {
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_ERROR_MESSAGE,orderErrorMessage.getId().toString(),orderErrorMessage);
        }

        customerProductStockService.redisInit();

        orderReshipInfoService.redisInitReshipOrderApplication();
        log.warn("redis客户商品库存加载完毕----------------------------");
    }
}
