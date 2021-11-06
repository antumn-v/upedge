package com.upedge.oms.modules.sales.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.product.CustomerProductDailySales;
import com.upedge.oms.modules.sales.entity.CustomerProductSalesLog;

import java.util.List;

/**
 * @author author
 */
public interface CustomerProductSalesLogDao{

    List<Long> selectVariantIdByCustomerId(Long customerId);

    List<CustomerProductDailySales> selectCustomerProductSales(Long customerId);

    CustomerProductSalesLog selectByPrimaryKey(CustomerProductSalesLog record);

    int deleteByPrimaryKey(CustomerProductSalesLog record);

    int updateByPrimaryKey(CustomerProductSalesLog record);

    int updateByPrimaryKeySelective(CustomerProductSalesLog record);

    int insert(CustomerProductSalesLog record);

    int insertSelective(CustomerProductSalesLog record);

    int insertByBatch(List<CustomerProductSalesLog> list);

    List<CustomerProductSalesLog> select(Page<CustomerProductSalesLog> record);

    long count(Page<CustomerProductSalesLog> record);

}
