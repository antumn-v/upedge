package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
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
        String settingName = "upload_store_track_code_type";
        String settingValue = "1";
        Page page = new Page();
        page.setPageSize(-1);
        List<CustomerSetting> customerSettings = new ArrayList<>();
        List<Customer> customers = customerService.select(page);
        customers.forEach(customer -> {
            CustomerSetting customerSetting = new CustomerSetting();
            customerSetting.setSettingName(settingName);
            customerSetting.setSettingValue(settingValue);
            customerSetting.setCustomerId(customer.getId());
            customerSettings.add(customerSetting);
        });
        insertByBatch(customerSettings);
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