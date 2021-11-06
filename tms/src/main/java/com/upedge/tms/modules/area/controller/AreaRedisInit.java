package com.upedge.tms.modules.area.controller;


import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.tms.ArearedisVo;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.area.service.AreaService;
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
public class AreaRedisInit {

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private AreaService areaService;

    /**
     * 项目启动时初始化地区数据到redis
     */
    @PostConstruct
    public void initArea(){
        log.info("国家地区表开始初始化");
        List<Area> areaList = areaService.allArea();
        Map<String, ArearedisVo> map=new HashMap<>();
        Map<String, Long> countryAreaId=new HashMap<>();
        for(Area area:areaList){
            ArearedisVo arearedisVo = new ArearedisVo();
            BeanUtils.copyProperties(area,arearedisVo);
            map.put(String.valueOf(area.getId()),arearedisVo);
            countryAreaId.put(area.getEnName(),area.getId());
        }

        redisTemplate.opsForHash().putAll(RedisKey.AREA,map);
        redisTemplate.opsForHash().putAll(RedisKey.HASH_COUNTRY_AREA_ID,countryAreaId);
        log.info("国家地区表初始化完成");
    }
}
