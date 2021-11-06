package com.upedge.tms.init;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.tms.SaiheTransportVo;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.service.SaiheTransportService;
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
public class SaiheTransportInit {

    @Autowired
    private SaiheTransportService saiheTransportService;


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void saiheTransport(){
        Page<SaiheTransport> page = new Page<>();
        page.setPageSize(-1);
        List<SaiheTransport> list = saiheTransportService.select(page);
        list.forEach(saiheTransport -> {
            SaiheTransportVo saiheTransportVo = new SaiheTransportVo();
            BeanUtils.copyProperties(saiheTransport,saiheTransportVo);
            redisTemplate.opsForValue().set(
                    RedisKey.STRING_SAIHE_TRANSPORT_IDKEY+saiheTransport.getId(),
                    saiheTransport
                    );
        });
        log.warn("saiheTransport加载完毕");
    }


}
