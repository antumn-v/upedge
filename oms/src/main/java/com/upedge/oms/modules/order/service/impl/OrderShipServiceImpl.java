package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.order.service.OrderShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderShipServiceImpl implements OrderShipService {


    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<ShipDetail> orderOverseasWarehouseShipMethods(Long orderId, Session session) {
        return null;
    }
}
