package com.upedge.oms.init;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.oms.modules.vat.service.VatRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class VatRedisInit {


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    VatRuleService vatRuleService;

    @PostConstruct
    public void packageCurrentUsdRateInit(){
        Set<String> keys = redisTemplate.keys(RedisKey.STRING_AREA_VAT_RULE + "*");
        redisTemplate.delete(keys);
    }
}
