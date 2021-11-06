package com.upedge.oms.modules.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/stock/advice/setting")
public class StockAdviceSettingController {


    @Autowired
    RedisTemplate<String, Object> redisTemplate;




}
