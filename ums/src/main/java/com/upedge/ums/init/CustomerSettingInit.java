package com.upedge.ums.init;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class CustomerSettingInit {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CustomerSettingService customerSettingService;

    @PostConstruct
    public void initCustomerSetting(){
        Page<CustomerSetting> page = new Page<>();
        page.setPageSize(-1);
        page.setOrderBy("customer_id desc");
        List<CustomerSetting> customerSettings = customerSettingService.select(page);
        Map<String,HashMap<String,String>> map = new HashMap<>();
        if (ListUtils.isNotEmpty(customerSettings)){
            for (CustomerSetting customerSetting : customerSettings) {
                HashMap<String,String> nameValueMap = null;
                String key = RedisKey.HASH_CUSTOMER_SETTING + customerSetting.getCustomerId();
                if (map.containsKey(key)){
                    nameValueMap = map.get(key);
                }else {
                    nameValueMap = new HashMap<>();
                    map.put(key,nameValueMap);
                }
                nameValueMap.put(customerSetting.getSettingName(),customerSetting.getSettingValue());
            }
        }
        for (Map.Entry<String, HashMap<String, String>> entry : map.entrySet()){
            redisTemplate.opsForHash().putAll(entry.getKey(),entry.getValue());
        }
        System.out.println("---------------客户设置加载完成-------------------");
    }
}
