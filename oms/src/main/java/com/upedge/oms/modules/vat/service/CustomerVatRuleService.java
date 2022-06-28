package com.upedge.oms.modules.vat.service;

import com.upedge.oms.modules.vat.entity.CustomerVatRule;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerVatRuleService{

    List<Long> selectCustomerIdsByRuleId(Long ruleId);

    void deleteByRuleId(Long ruleId);

    CustomerVatRule selectByPrimaryKey(Long customerId);

    int deleteByPrimaryKey(Long customerId);

    int updateByPrimaryKey(CustomerVatRule record);

    int updateByPrimaryKeySelective(CustomerVatRule record);

    int insert(CustomerVatRule record);

    int insertByBatch(List<CustomerVatRule> records);

    int insertSelective(CustomerVatRule record);

    List<CustomerVatRule> select(Page<CustomerVatRule> record);

    long count(Page<CustomerVatRule> record);
}

