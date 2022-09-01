package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.ums.modules.user.dao.CustomerDao;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.vo.CustomerDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Customer record = new Customer();
        record.setId(id);
        return customerDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Customer record) {
        return customerDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Customer record) {
        return customerDao.insert(record);
    }

    @Override
    public BaseResponse selectCustomerDetail(Page<CustomerDetailVo> customerDetailVoPage) {
        if (null == customerDetailVoPage){
            return BaseResponse.failed();
        }
        List<CustomerDetailVo> customerDetailVos =  customerDao.selectCustomerDetail(customerDetailVoPage);
        Long total = customerDao.countCustomerDetail(customerDetailVoPage);
        try {
            Set<String> storeKeys = redisTemplate.keys(RedisKey.STRING_STORE + "*");
            List<StoreVo> storeVos = redisTemplate.opsForValue().multiGet(storeKeys);
            for (CustomerDetailVo customerDetailVo : customerDetailVos) {
                Long customerId = customerDetailVo.getId();
                customerDetailVo.setStores(new ArrayList<>());
                for (StoreVo storeVo : storeVos) {
                    if (storeVo.getCustomerId().equals(customerId)){
                        customerDetailVo.getStores().add(storeVo);
                    }
                }
                storeVos.removeAll(customerDetailVo.getStores());

                List<CustomerSetting> customerSettings = customerDetailVo.getCustomerSettings();
                Map<String,String> map = redisTemplate.opsForHash().entries(RedisKey.HASH_CUSTOMER_SETTING + customerId);
                for (Map.Entry<String,String> setting : map.entrySet()){
                    CustomerSetting customerSetting = new CustomerSetting();
                    customerSetting.setCustomerId(customerId);
                    customerSetting.setSettingName(setting.getKey());
                    customerSetting.setSettingValue(setting.getValue());
                    customerSettings.add(customerSetting);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerDetailVoPage.setTotal(total);
        return BaseResponse.success(customerDetailVos,customerDetailVoPage);
    }

    /**
     *
     */
    public Customer selectByPrimaryKey(Long id){
        Customer record = new Customer();
        record.setId(id);
        return customerDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Customer record) {
        return customerDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Customer record) {
        return customerDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Customer> select(Page<Customer> record){
        record.initFromNum();
        return customerDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Customer> record){
        return customerDao.count(record);
    }

}