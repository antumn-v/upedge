package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.ums.modules.user.dao.CustomerVipRecordDao;
import com.upedge.ums.modules.user.entity.Customer;
import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import com.upedge.ums.modules.user.service.CustomerService;
import com.upedge.ums.modules.user.service.CustomerVipRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class CustomerVipRecordServiceImpl implements CustomerVipRecordService {

    @Autowired
    private CustomerVipRecordDao customerVipRecordDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CustomerService customerService;



    /**
     *
     */
    
    public int deleteByPrimaryKey(Integer id) {
        CustomerVipRecord record = new CustomerVipRecord();
        record.setId(id);
        return customerVipRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    
    public int insert(CustomerVipRecord record) {
        return insertSelective(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerVipRecord record) {
        Customer customer = customerService.selectByPrimaryKey(record.getCustomerId());
        if (customer.getVipLevel() == record.getVipType()){
            return 1;
        }
        customer = new Customer();
        customer.setId(record.getCustomerId());
        customer.setVipLevel(record.getVipType());
        customerService.updateByPrimaryKeySelective(customer);
        int i = customerVipRecordDao.insert(record);
        if(record.getVipType() == CustomerVipRecord.VIP_AUTHORIZE){
            redisTemplate.opsForHash().put(RedisKey.HASH_CUSTOMER_VIP_REBATE,record.getCustomerId().toString(),new BigDecimal("0.2"));
        }else{
            redisTemplate.opsForHash().delete(RedisKey.HASH_CUSTOMER_VIP_REBATE,record.getCustomerId().toString());
        }

        return i;
    }

    /**
     *
     */
    public CustomerVipRecord selectByPrimaryKey(Integer id){
        CustomerVipRecord record = new CustomerVipRecord();
        record.setId(id);
        return customerVipRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    
    public int updateByPrimaryKeySelective(CustomerVipRecord record) {
        return customerVipRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    
    public int updateByPrimaryKey(CustomerVipRecord record) {
        return customerVipRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerVipRecord> select(Page<CustomerVipRecord> record){
        record.initFromNum();
        return customerVipRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerVipRecord> record){
        return customerVipRecordDao.count(record);
    }

}