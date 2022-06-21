package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderReshipInfoDao;
import com.upedge.oms.modules.order.entity.OrderReshipInfo;
import com.upedge.oms.modules.order.service.OrderReshipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderReshipInfoServiceImpl implements OrderReshipInfoService {


    @Autowired
    OrderReshipInfoDao orderReshipInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void redisInitReshipOrderApplication() {
        OrderReshipInfo orderReshipInfo = new OrderReshipInfo();
        orderReshipInfo.setCreateApplicationId(Constant.APP_APPLICATION_ID);
        Page<OrderReshipInfo> page = new Page<>();
        page.setPageSize(-1);
        page.setT(orderReshipInfo);

        List<Long> ids = new ArrayList<>();
        List<OrderReshipInfo> orderReshipInfos = orderReshipInfoDao.select(page);
        for (OrderReshipInfo reshipInfo : orderReshipInfos) {
            ids.add(reshipInfo.getOrderId());
        }
        redisTemplate.delete(RedisKey.HASH_ORDER_APP_CREATE_RESHIP_APPLICATION);
        if (ListUtils.isNotEmpty(ids)){
            redisTemplate.opsForList().leftPushAll(RedisKey.HASH_ORDER_APP_CREATE_RESHIP_APPLICATION,ids);
        }
    }
}
