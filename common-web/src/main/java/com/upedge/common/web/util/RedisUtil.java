package com.upedge.common.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

    //备库 创建备库单 接口幂等KEY
    public static final String KEY_STOCK_CREATE_PROCUREMENT="stock:createProcurement:";
    //备库 申请退款 接口幂等KEY
    public static final String KEY_STOCK_APPLY_REFUND="stock:applyRefund:";
    //备库 处理退款 接口幂等KYE
    public static final String KEY_STOCK_PROCESS_REFUND="stock:processRefund:";

    //普通订单 利润计算 接口幂等KYE
    public static final String KEY_ORDER_PROFIT_UPDATE="order:profit:update:";
    //普通订单 申请补发 接口幂等KYE
    public static final String KEY_ORDER_APPLY_RESHIP="order:applyReship:";
    //普通订单 申请退款 接口幂等KEY
    public static final String KEY_ORDER_APPLY_REFUND="order:applyRefund:";
    //普通订单 处理退款 接口幂等KYE
    public static final String KEY_ORDER_PROCESS_REFUND="order:processRefund:";
    //普通订单 导入赛盒 接口幂等KYE
    public static final String KEY_ORDER_IMPORT_SAIHE="order:importSaihe:";
    //普通订单 赛盒物流 接口幂等KYE
    public static final String KEY_ORDER_SAIHE_TRACK="order:saiheTrack:";

    //运输单元导入 接口幂等KYE
    public static final String KEY_SHIP_UNIT_IMPORT="shippingUnitImport";

    //批发订单 申请补发 接口幂等KYE
    public static final String KEY_WHOLESALE_APPLY_RESHIP="wholesale:applyReship:";
    //批发订单 申请退款 接口幂等KEY
    public static final String KEY_WHOLESALE_APPLY_REFUND="wholesale:applyRefund:";
    //批发订单 处理退款 接口幂等KYE
    public static final String KEY_WHOLESALE_PROCESS_REFUND="wholesale:processRefund:";
    //批发订单 导入赛盒 接口幂等KYE
    public static final String KEY_WHOLESALE_IMPORT_SAIHE="wholesale:importSaihe:";
    //批发订单 赛盒物流 接口幂等KYE
    public static final String KEY_WHOLESALE_SAIHE_TRACK="wholesale:saiheTrack:";

    public static final String KEY_PACKAGE_UPDATE="package:update";

    /**
     * 如果已经存在返回false,否则返回true
     */
    public static Boolean setNx(RedisTemplate<String, Object> redisTemplate, String key, Object value, Long expireTime, TimeUnit timeUnit){
        if(StringUtils.isBlank(key) |value==null){
            return false;
        }
        return redisTemplate.opsForValue().setIfAbsent(key,value,expireTime,timeUnit);
    }

    /**
     * 获取数据
     */
    public static Object get(RedisTemplate<String, Object> redisTemplate, String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 移除数据
     */
    public static Boolean remove(RedisTemplate<String, Object> redisTemplate, String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return redisTemplate.delete(key);
    }

    /**
     * 加锁
     * @param key
     * @param waitTime 等待时间
     * @param expireTime 过期时间
     */
    public static Boolean lock(RedisTemplate<String, Object> redisTemplate, String key, Long waitTime, Long expireTime) {

        String value = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        Boolean flag = setNx(redisTemplate,key, value, expireTime, TimeUnit.MILLISECONDS);
        // 尝试获取锁 成功返回
        if (flag) {
            return true;
        } else {
            // 获取失败
            // 现在时间
            long newTime = System.currentTimeMillis();
            // 等待过期时间
            long loseTime = newTime + waitTime;
            // 不断尝试获取锁成功返回
            while (System.currentTimeMillis() < loseTime) {
                Boolean retryFlag = setNx(redisTemplate,key, value, expireTime, TimeUnit.MILLISECONDS);
                if (retryFlag) {
                    return retryFlag;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param key
     * @return
     */
    public static Boolean unLock(RedisTemplate<String, Object> redisTemplate, String key) {
        return remove(redisTemplate,key);
    }
}