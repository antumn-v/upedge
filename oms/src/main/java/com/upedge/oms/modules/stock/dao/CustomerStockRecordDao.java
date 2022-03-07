package com.upedge.oms.modules.stock.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;

import java.util.List;

/**
 * @author author
 */
public interface CustomerStockRecordDao{

    CustomerStockRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(CustomerStockRecord record);

    int updateByPrimaryKey(CustomerStockRecord record);

    int updateByPrimaryKeySelective(CustomerStockRecord record);

    int insert(CustomerStockRecord record);

    int insertSelective(CustomerStockRecord record);

    int insertByBatch(List<CustomerStockRecord> list);

    List<CustomerStockRecord> select(Page<CustomerStockRecord> record);

    long count(Page<CustomerStockRecord> record);

}
