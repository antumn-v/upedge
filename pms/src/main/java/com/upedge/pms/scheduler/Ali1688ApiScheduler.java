package com.upedge.pms.scheduler;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Ali1688ApiScheduler {

    @Autowired
    AlibabaApiService alibabaApiService;

    @Autowired
    RedisTemplate redisTemplate;

    @Scheduled(cron = "0 00 */1 ? * *")
    public void refreshAli1688Api(){
        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
        long now = System.currentTimeMillis();
        long time = alibabaApiVo.getAccessTokenExpireTime() - now ;
        if (time <= 60 * 60 * 1000){
            alibabaApiVo = Ali1688Service.refreshAccessToken(alibabaApiVo);
            if (null == alibabaApiVo){
                return;
            }
            AlibabaApi alibabaApi = new AlibabaApi();
            BeanUtils.copyProperties(alibabaApiVo,alibabaApi);
            alibabaApiService.updateByPrimaryKey(alibabaApi);
            redisTemplate.opsForValue().set(RedisKey.STRING_ALI1688_API,alibabaApiVo);
        }

    }
}
