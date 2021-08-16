package com.upedge.common.utils;

public class RedisKeyUtils {

    public static String getCustomerSettingKey(Long customerId){
        return new StringBuffer(String.valueOf(customerId)).append(":app:setting").toString();
    }
}
