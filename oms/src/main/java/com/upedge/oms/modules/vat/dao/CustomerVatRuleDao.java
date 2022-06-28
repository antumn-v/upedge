package com.upedge.oms.modules.vat.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.vat.entity.CustomerVatRule;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerVatRuleDao{

    List<Long> selectCustomerIdsByRuleId(Long ruleId);

    void deleteByRuleId(Long ruleId);

    CustomerVatRule selectByPrimaryKey(CustomerVatRule record);

    int deleteByPrimaryKey(CustomerVatRule record);

    int updateByPrimaryKey(CustomerVatRule record);

    int updateByPrimaryKeySelective(CustomerVatRule record);

    int insert(CustomerVatRule record);

    int insertSelective(CustomerVatRule record);

    int insertByBatch(List<CustomerVatRule> list);

    List<CustomerVatRule> select(Page<CustomerVatRule> record);

    long count(Page<CustomerVatRule> record);

}
