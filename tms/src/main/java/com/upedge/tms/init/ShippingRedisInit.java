package com.upedge.tms.init;

import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import com.upedge.tms.modules.ship.service.ShippingMethodService;
import com.upedge.tms.modules.ship.service.ShippingMethodTemplateService;
import com.upedge.tms.modules.ship.service.ShippingTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ShippingRedisInit {

    @Autowired
    ShippingMethodService shippingMethodService;
    @Autowired
    ShippingMethodTemplateService shippingMethodTemplateService;
    @Autowired
    private ShippingTemplateService   shippingTemplateService;
    @Resource
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void initData(){
        List<ShippingMethod> shippingMethodList=shippingMethodService.allShippingMethod();
        Map<String, ShippingMethodRedis> map=new HashMap<>();
        for(ShippingMethod shippingMethod:shippingMethodList){
            ShippingMethodRedis shippingMethodRedis=new ShippingMethodRedis();
            BeanUtils.copyProperties(shippingMethod,shippingMethodRedis);
            map.put(String.valueOf(shippingMethod.getId()),shippingMethodRedis);
        }
        redisTemplate.opsForHash().putAll(RedisKey.SHIPPING_METHOD,map);
        log.info("运输方式数据初始化成功。。。");
        List<ShippingMethodTemplate> shippingMethodTemplateList=shippingMethodTemplateService.listShippingMethodTemplate();
        for(ShippingMethodTemplate shippingMethodTemplate:shippingMethodTemplateList){
            String key= String.valueOf(shippingMethodTemplate.getShippingId());
            Long methodId=shippingMethodTemplate.getMethodId();
            redisTemplate.opsForSet().add(RedisKey.SHIPPING_METHODS+key,methodId);
        }
        log.info("运输方式-运输属性数据初始化成功。。。");


        log.info("运输模板表开始初始化…………");
        List<ShippingTemplate> templateList  = shippingTemplateService.getAll();
        Map<String, ShippingTemplateRedis> templateMap=new HashMap<>();
        for(ShippingTemplate template:templateList){
            ShippingTemplateRedis shippingTemplateRedis = new ShippingTemplateRedis();
            BeanUtils.copyProperties(template,shippingTemplateRedis);
            templateMap.put(String.valueOf(template.getId()),shippingTemplateRedis);
        }
        redisTemplate.opsForHash().putAll(RedisKey.SHIPPING_TEMPLATE,templateMap);
        log.info("运输模板表数据初始化成功。。。");

    }

}