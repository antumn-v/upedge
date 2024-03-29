package com.upedge.ums.init;


import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.AffiliateVo;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import com.upedge.ums.modules.user.entity.CustomerIoss;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.entity.User;
import com.upedge.ums.modules.user.service.CustomerIossService;
import com.upedge.ums.modules.user.service.CustomerSettingService;
import com.upedge.ums.modules.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class RedisInit {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StoreService storeService;

    @Autowired
    CustomerIossService customerIossService;

    @Autowired
    CustomerSettingService customerSettingService;

    @Autowired
    AffiliateService affiliateService;

    @Autowired
    CustomerManagerService customerManagerService;

    @Autowired
    ManagerInfoService managerInfoService;

    @Autowired
    UserService userService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @PostConstruct
    public void redisInit(){
        CompletableFuture storeInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                storeInit();
            }
        },threadPoolExecutor);

        CompletableFuture<Void> iossInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                customerIossInit();
            }
        },threadPoolExecutor);

        CompletableFuture<Void> affiliateInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                affiliateInit();
            }
        },threadPoolExecutor);

        CompletableFuture<Void> customerManagerInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                customerManagerInit();
            }
        },threadPoolExecutor);

        CompletableFuture<Void> customerInfoInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                customerInfoInit();
            }
        },threadPoolExecutor);

        CompletableFuture<Void> customerSettingInit = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                initCustomerSetting();
            }
        },threadPoolExecutor);

        try {
            CompletableFuture.allOf(storeInit,iossInit,affiliateInit,customerManagerInit,customerInfoInit,customerSettingInit).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public void affiliateInit(){
        Set<String> keys = redisTemplate.keys(RedisKey.HASH_AFFILIATE_REFEREE + "*");
        redisTemplate.delete(keys);
        List<Affiliate> affiliates = affiliateService.allAffiliates();
        for (Affiliate affiliate : affiliates) {
            if (!affiliate.getRebateState()){
                continue;
            }
            AffiliateVo affiliateVo = new AffiliateVo();
            BeanUtils.copyProperties(affiliate,affiliateVo);
            redisTemplate.opsForList().leftPush(RedisKey.HASH_AFFILIATE_REFEREE + affiliateVo.getRefereeId(),affiliateVo);
        }
    }

    public void storeInit(){

        Page<Store> page = new Page<>();
        page.setPageSize( -1);
        List<Store> stores = storeService.select(page);
        stores.forEach(store -> {
            StoreVo storeVo = new StoreVo();
            BeanUtils.copyProperties(store,storeVo);
            redisTemplate.opsForValue().set(RedisKey.STRING_STORE + store.getId(),storeVo);
        });
        log.warn("-------------------店铺信息加载完毕-----------------");
    }


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

    public void customerInfoInit(){
        Map<String, UserVo> userVoMap = new HashMap<>();
        List<User> users = userService.selectAllDefaultCustomerInfo();
        for (User user : users) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user,userVo);
            if (StringUtils.isBlank(userVo.getRemark())){
                userVo.setRemark("");
            }
            userVoMap.put(String.valueOf(user.getCustomerId()),userVo);
        }
        redisTemplate.delete(RedisKey.STRING_CUSTOMER_INFO);
        redisTemplate.opsForHash().putAll(RedisKey.STRING_CUSTOMER_INFO,userVoMap);
    }

    public void customerManagerInit(){
        List<CustomerManager> customerManagers = customerManagerService.selectAll();
        if (ListUtils.isEmpty(customerManagers)){
            return;
        }
        Map<String,String> map = new HashMap<>();
        for (CustomerManager customerManager : customerManagers) {
            map.put(customerManager.getCustomerId().toString(), customerManager.getManagerCode());
        }
        redisTemplate.opsForHash().putAll(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,map);


        List<ManagerInfoVo> managerInfoVos = managerInfoService.selectManagerDetail();
        redisTemplate.delete(RedisKey.STRING_MANAGER_INFO + "*");
        for (ManagerInfoVo managerInfoVo : managerInfoVos) {
            redisTemplate.opsForValue().set(RedisKey.STRING_MANAGER_INFO + managerInfoVo.getManagerCode(),managerInfoVo);
        }
    }


    public void initCustomerSetting(){
        Page<CustomerSetting> page = new Page<>();
        page.setPageSize(-1);
        List<CustomerSetting> customerSettings = customerSettingService.select(page);
        Map<String, HashMap<String,String>> map = new HashMap<>();
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
