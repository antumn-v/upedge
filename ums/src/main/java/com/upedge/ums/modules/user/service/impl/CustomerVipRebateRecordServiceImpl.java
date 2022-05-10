package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.ums.modules.account.entity.Account;
import com.upedge.ums.modules.account.service.AccountService;
import com.upedge.ums.modules.user.dao.CustomerVipRebateRecordDao;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import com.upedge.ums.modules.user.service.CustomerVipRebateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
public class CustomerVipRebateRecordServiceImpl implements CustomerVipRebateRecordService {

    @Autowired
    private CustomerVipRebateRecordDao customerVipRebateRecordDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    AccountService accountService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerVipRebateRecord record = new CustomerVipRebateRecord();
        record.setId(id);
        return customerVipRebateRecordDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerVipRebateRecord record) {
        return customerVipRebateRecordDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerVipRebateRecord record) {
        return customerVipRebateRecordDao.insert(record);
    }

    @Override
    public void addCustomerVipRebate(Long customerId,Long orderId) {
        BigDecimal rebate = (BigDecimal) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_VIP_REBATE,customerId);
        if (rebate == null){
            return;
        }
        CustomerVipRebateRecord record = customerVipRebateRecordDao.selectByOrderId(orderId);
        if (null != record){
            return;
        }
        Account account = accountService.selectCustomerDefaultAccount(customerId);
        CustomerVipRebateRecord customerVipRebateRecord = new CustomerVipRebateRecord(customerId, account.getId(),orderId,rebate,1,new Date());
        insert(customerVipRebateRecord);
        accountService.addAccountVipRebate(account.getId(), rebate);
    }

    /**
     *
     */
    public CustomerVipRebateRecord selectByPrimaryKey(Long id){
        CustomerVipRebateRecord record = new CustomerVipRebateRecord();
        record.setId(id);
        return customerVipRebateRecordDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerVipRebateRecord record) {
        return customerVipRebateRecordDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerVipRebateRecord record) {
        return customerVipRebateRecordDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerVipRebateRecord> select(Page<CustomerVipRebateRecord> record){
        record.initFromNum();
        return customerVipRebateRecordDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerVipRebateRecord> record){
        return customerVipRebateRecordDao.count(record);
    }

}