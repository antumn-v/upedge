package com.upedge.oms.modules.pack.service;

import com.upedge.oms.modules.pack.entity.OrderPackageImportLog;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPackageImportLogService{

    OrderPackageImportLog selectByOrderInfo( String storeName,  String platOrderName,  String trackingCode);

    OrderPackageImportLog selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(OrderPackageImportLog record);

    int updateByPrimaryKeySelective(OrderPackageImportLog record);

    int insert(OrderPackageImportLog record);

    int insertSelective(OrderPackageImportLog record);

    List<OrderPackageImportLog> select(Page<OrderPackageImportLog> record);

    long count(Page<OrderPackageImportLog> record);
}

