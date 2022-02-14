package com.upedge.pms.init;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AlibabaConfigInit {


    @Autowired
    AlibabaApiService alibabaApiService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void AlibabaConfigInit(){
        long now = System.currentTimeMillis();
        com.upedge.pms.modules.alibaba.entity.AlibabaApi alibabaApi = alibabaApiService.selectAlibabaApi();
        AlibabaApiVo alibabaApiVo = new AlibabaApiVo();
        BeanUtils.copyProperties(alibabaApi,alibabaApiVo);
        long time = alibabaApi.getAccessTokenExpireTime() - now ;
        if (time <= 60 * 60 * 1000){
            alibabaApiVo = com.upedge.pms.modules.alibaba.service.Ali1688Service.refreshAccessToken(alibabaApiVo);
            if (null == alibabaApiVo){
                return;
            }
            BeanUtils.copyProperties(alibabaApiVo,alibabaApi);
            alibabaApiService.updateByPrimaryKey(alibabaApi);
        }
        redisTemplate.opsForValue().set(RedisKey.STRING_ALI1688_API,alibabaApiVo);
    }

}
