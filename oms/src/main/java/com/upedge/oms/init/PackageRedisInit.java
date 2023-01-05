package com.upedge.oms.init;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.oms.modules.pack.service.PackageUsdRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Slf4j
@Component
public class PackageRedisInit {

    @Autowired
    PackageUsdRateService packageUsdRateService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void packageCurrentUsdRateInit(){

        BigDecimal rate = packageUsdRateService.currentMonthUsdRate();

        redisTemplate.opsForValue().set(RedisKey.STRING_PACKAGE_CURRENT_MONTH_USD_RATE,rate);
        log.warn("本月包裹美元汇率加载完毕。。。。{}",rate);
    }
}
