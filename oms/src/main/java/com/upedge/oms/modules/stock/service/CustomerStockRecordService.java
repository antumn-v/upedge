package com.upedge.oms.modules.stock.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;

import java.util.List;

/**
 * @author author
 */
public interface CustomerStockRecordService{

    int saveDischargeStockRecordByPaymentId(Long customerId, Long paymentId, Integer orderType);

    CustomerStockRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerStockRecord record);

    int updateByPrimaryKeySelective(CustomerStockRecord record);

    int insert(CustomerStockRecord record);

    int insertByBatch(List<CustomerStockRecord> records);

    int insertSelective(CustomerStockRecord record);

    List<CustomerStockRecord> select(Page<CustomerStockRecord> record);

    long count(Page<CustomerStockRecord> record);
}

