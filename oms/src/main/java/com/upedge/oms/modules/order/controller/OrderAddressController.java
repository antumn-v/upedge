package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.request.OrderAddressUpdateRequest;
import com.upedge.oms.modules.order.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orderAddress")
public class OrderAddressController {

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/update/{id}")
    public BaseResponse updateOrderAddress(@PathVariable Long id, @RequestBody@Valid OrderAddressUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        OrderAddress address = request.toOrderAddress(id);
        orderAddressService.update(address,session);
        return BaseResponse.success();
    }
}
