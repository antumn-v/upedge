package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerSettingEnum;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.modules.user.dao.CustomerSettingDao;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.CustomerSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerSettingServiceImpl implements CustomerSettingService {

    @Autowired
    private CustomerSettingDao customerSettingDao;

    @Autowired
    CustomerService customerService;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        CustomerSetting record = new CustomerSetting();
        record.setId(id);
        return customerSettingDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerSetting record) {
        return customerSettingDao.insert(record);
    }

    @Override
    public int insertByBatch(List<CustomerSetting> customerSettings) {
        return customerSettingDao.insertByBatch(customerSettings);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerSetting record) {
        return customerSettingDao.insert(record);
    }

    @Override
    public void saveNewSetting() {
        Page<Customer> page = new Page<>();
        page.setPageSize(-1);
        List<Customer> customers = customerService.select(page);
        for (Customer customer : customers) {
            List<CustomerSetting> customerSettings = customerSettingDao.selectByCustomerId(customer.getId());
            if (ListUtils.isNotEmpty(customerSettings)){
                continue;
            }
            saveNewSetting(customer.getId());
        }

    }

    @Override
    public void saveNewSetting(Long customerId) {

        List<CustomerSetting> customerSettings = new ArrayList<>();
        String key = RedisKey.HASH_CUSTOMER_SETTING + customerId;
        for (CustomerSettingEnum customerSettingEnum : CustomerSettingEnum.values()) {

            CustomerSetting customerSetting = new CustomerSetting();
            customerSetting.setCustomerId(customerId);
            customerSetting.setSettingName(customerSettingEnum.name());
            customerSetting.setSettingValue(customerSettingEnum.getValue());

            redisTemplate.opsForHash().put(key,customerSettingEnum.name(),customerSettingEnum.getValue());
            customerSettings.add(customerSetting);
        }
        if (0 < customerSettings.size()) {
            insertByBatch(customerSettings);
        }

    }

    @Override
    public CustomerSetting selectByCustomerAndSettingName(Long customerId, String settingName) {
        return customerSettingDao.selectByCustomerAndSettingName(customerId, settingName);
    }

    /**
     *
     */
    public CustomerSetting selectByPrimaryKey(Integer id){
        CustomerSetting record = new CustomerSetting();
        record.setId(id);
        return customerSettingDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerSetting record) {
        String key = RedisKey.HASH_CUSTOMER_SETTING + record.getCustomerId();
        redisTemplate.opsForHash().put(key,record.getSettingName(),record.getSettingValue());
        return customerSettingDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerSetting record) {
        return customerSettingDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerSetting> select(Page<CustomerSetting> record){
        record.initFromNum();
        return customerSettingDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerSetting> record){
        return customerSettingDao.count(record);
    }

}