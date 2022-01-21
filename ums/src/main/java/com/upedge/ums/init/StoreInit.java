package com.upedge.ums.init;


import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class StoreInit {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StoreService storeService;


    @PostConstruct
    public void storeInit(){
        Page<Store> page = new Page<>();
        page.setPageSize( -1);
        List<Store> stores = storeService.select(page);
        stores.forEach(store -> {
            StoreVo storeVo = new StoreVo();
            BeanUtils.copyProperties(store,storeVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(),storeVo);
        });
        log.warn("-------------------店铺信息加载完毕-----------------");


    }

}
