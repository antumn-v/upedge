package com.upedge.pms.init;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import com.upedge.pms.modules.product.service.StoreProductVariantService;
import com.upedge.pms.modules.product.vo.SplitVariantIdVo;
import com.upedge.pms.modules.quote.service.QuoteApplyItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RedisInit {

    @Autowired
    StoreProductVariantService storeProductVariantService;

    @Autowired
    AlibabaApiService alibabaApiService;

    @Autowired
    QuoteApplyItemService quoteApplyItemService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 1688token
     */
    @PostConstruct
    public void AlibabaConfigInit(){
        long now = System.currentTimeMillis();
        AlibabaApi alibabaApi = alibabaApiService.selectAlibabaApi();
        AlibabaApiVo alibabaApiVo = new AlibabaApiVo();
        BeanUtils.copyProperties(alibabaApi,alibabaApiVo);
        long time = alibabaApi.getAccessTokenExpireTime() - now ;
        if (time <= 60 * 60 * 1000){
            alibabaApiVo = Ali1688Service.refreshAccessToken(alibabaApiVo);
            if (null == alibabaApiVo){
                return;
            }
            BeanUtils.copyProperties(alibabaApiVo,alibabaApi);
            alibabaApiService.updateByPrimaryKey(alibabaApi);
        }
        redisTemplate.opsForValue().set(RedisKey.STRING_ALI1688_API,alibabaApiVo);
    }

    /**
     * 拆分变体集合
     */
    @PostConstruct
    public void splitVariantIdListInit(){
        List<SplitVariantIdVo> splitVariantIds = storeProductVariantService.selectSplitVariantIds();
        Map<String,List<Long>> map = new HashMap<>();
        for (SplitVariantIdVo splitVariantId : splitVariantIds) {
            map.put(splitVariantId.getParentVariantId(), splitVariantId.getSplitVariantIds());
        }
        redisTemplate.delete(RedisKey.HASH_STORE_SPLIT_VARIANT);
        redisTemplate.opsForHash().putAll(RedisKey.HASH_STORE_SPLIT_VARIANT,map);
        log.warn("已拆分变体ID集合初始化完成-------------------------------------");


        //所有报价中的店铺产品ID
        List<Long> quotingVariantIds = quoteApplyItemService.selectAllQuotingStoreVariantIds();
        redisTemplate.delete(RedisKey.LIST_QUOTING_STORE_VARIANT);
        redisTemplate.opsForList().leftPushAll(RedisKey.LIST_QUOTING_STORE_VARIANT,quotingVariantIds);
        log.warn("报价中变体ID集合初始化完成-------------------------------------");
    }

}
