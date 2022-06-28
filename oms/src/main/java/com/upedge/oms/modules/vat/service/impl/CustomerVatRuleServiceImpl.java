package com.upedge.oms.modules.vat.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.vat.dao.CustomerVatRuleDao;
import com.upedge.oms.modules.vat.entity.CustomerVatRule;
import com.upedge.oms.modules.vat.service.CustomerVatRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerVatRuleServiceImpl implements CustomerVatRuleService {

    @Autowired
    private CustomerVatRuleDao customerVatRuleDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long customerId) {
        CustomerVatRule record = new CustomerVatRule();
        record.setCustomerId(customerId);
        return customerVatRuleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerVatRule record) {
        return customerVatRuleDao.insert(record);
    }

    @Override
    public int insertByBatch(List<CustomerVatRule> records) {
        if (ListUtils.isNotEmpty(records)){
            customerVatRuleDao.insertByBatch(records);
        }
        return 0;
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerVatRule record) {
        return customerVatRuleDao.insert(record);
    }

    @Override
    public List<Long> selectCustomerIdsByRuleId(Long ruleId) {
        return customerVatRuleDao.selectCustomerIdsByRuleId(ruleId);
    }

    @Override
    public void deleteByRuleId(Long ruleId) {
        customerVatRuleDao.deleteByRuleId(ruleId);
    }

    /**
     *
     */
    public CustomerVatRule selectByPrimaryKey(Long customerId){
        CustomerVatRule record = new CustomerVatRule();
        record.setCustomerId(customerId);
        return customerVatRuleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerVatRule record) {
        return customerVatRuleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerVatRule record) {
        return customerVatRuleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerVatRule> select(Page<CustomerVatRule> record){
        record.initFromNum();
        return customerVatRuleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerVatRule> record){
        return customerVatRuleDao.count(record);
    }

}