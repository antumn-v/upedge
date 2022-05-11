package com.upedge.ums.modules.user.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerVipRebateRecordService{

    void addCustomerVipRebate(Long customerId,Long orderId);

    CustomerVipRebateRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerVipRebateRecord record);

    int updateByPrimaryKeySelective(CustomerVipRebateRecord record);

    int insert(CustomerVipRebateRecord record);

    int insertByBatch(List<CustomerVipRebateRecord> records);

    int insertSelective(CustomerVipRebateRecord record);

    List<CustomerVipRebateRecord> select(Page<CustomerVipRebateRecord> record);

    long count(Page<CustomerVipRebateRecord> record);
}

