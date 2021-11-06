package com.upedge.oms.modules.redis.impl;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.oms.modules.redis.OmsRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class OmsRedisServiceImpl implements OmsRedisService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Override
    public ManagerInfoVo getCustomerManager(String managerCode, Long customerId) {
        ManagerInfoVo managerInfoVo = null;
        String key = null;
        if (null == managerCode) {
            managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, String.valueOf(customerId));
        }
        key = RedisKey.STRING_MANAGER_INFO + managerCode;

        managerInfoVo = (ManagerInfoVo) redisTemplate.opsForValue().get(key);
        if (null == managerInfoVo) {

            BaseResponse response = umsFeignClient.getCustomerManager(customerId);
            if (response.getCode() == ResultCode.FAIL_CODE || null == response.getData()) {
                return null;
            }
            managerInfoVo = JSONObject.parseObject(response.getData().toString()).toJavaObject(ManagerInfoVo.class);
            redisTemplate.opsForValue().set(key, managerInfoVo);
        }
        return managerInfoVo;
    }

    //Wooc插件回传 未配置运输方式缓存
    @Override
    public Set<Object> getWoocShipMethod(Long customerId) {
        String key = RedisKey.WOOC_SHIP_METHOD + customerId;
        return redisTemplate.opsForSet().members(key);
    }

    //Wooc插件回传 未配置运输方式缓存
    @Override
    public void setWoocShipMethod(Long customerId, String methodName) {
        String key = RedisKey.WOOC_SHIP_METHOD + customerId;
        redisTemplate.opsForSet().add(key, methodName);
        redisTemplate.expire(key, 6, TimeUnit.HOURS);
    }

    //回传物流 店铺信息缓存
    @Override
    public StoreVo getStoreVo(Long storeId) {
        String key = RedisKey.HASH_TRACK_STORE;
        StoreVo storeVo = (StoreVo) redisTemplate.opsForHash().get(key, storeId);
        return storeVo;
    }

    //回传物流 店铺信息缓存
    @Override
    public void setStoreVo(Long storeId, StoreVo storeVo) {
        String key = RedisKey.HASH_TRACK_STORE;
        redisTemplate.opsForHash().put(key, storeId, storeVo);
        redisTemplate.expire(key, 6, TimeUnit.HOURS);
    }
}
