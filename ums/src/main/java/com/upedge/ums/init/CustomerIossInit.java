package com.upedge.ums.init;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.ums.modules.user.entity.CustomerIoss;
import com.upedge.ums.modules.user.service.CustomerIossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
//@Component
public class CustomerIossInit {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CustomerIossService customerIossService;

    @PostConstruct
    public void customerIossInit(){
        Page<CustomerIoss> page = new Page<>();
        CustomerIoss customerIoss = new CustomerIoss();
        customerIoss.setState(1);
        page.setPageSize(-1);
        page.setT(customerIoss);
        List<CustomerIoss> customerIossList = customerIossService.select(page);
        for (CustomerIoss ioss : customerIossList) {
            CustomerIossVo customerIossVo = new CustomerIossVo();
            BeanUtils.copyProperties(ioss,customerIossVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_CUSTOMER_IOSS + ioss.getCustomerId(),customerIossVo);
        }
        log.warn("客户IOSS信息加载完毕");
    }
}
