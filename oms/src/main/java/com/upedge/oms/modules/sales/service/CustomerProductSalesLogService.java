package com.upedge.oms.modules.sales.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.sales.entity.CustomerProductSalesLog;

import java.util.Date;
import java.util.List;

/**
 * @author author
 */
public interface CustomerProductSalesLogService{

    public void saveProductSaleRecord(Long paymentId, Integer orderType, Long customerId, Date date);

    CustomerProductSalesLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(CustomerProductSalesLog record);

    int updateByPrimaryKeySelective(CustomerProductSalesLog record);

    int insert(CustomerProductSalesLog record);

    int insertSelective(CustomerProductSalesLog record);

    List<CustomerProductSalesLog> select(Page<CustomerProductSalesLog> record);

    long count(Page<CustomerProductSalesLog> record);
}

