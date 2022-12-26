package com.upedge.oms.modules.pack.dao;

import com.upedge.oms.modules.pack.entity.OrderPackageImportLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface OrderPackageImportLogDao{

    OrderPackageImportLog selectByOrderInfo(@Param("storeName") String storeName, @Param("platOrderName") String platOrderName, @Param("trackingCode") String trackingCode);

    OrderPackageImportLog selectByPrimaryKey(OrderPackageImportLog record);

    int deleteByPrimaryKey(OrderPackageImportLog record);

    int updateByPrimaryKey(OrderPackageImportLog record);

    int updateByPrimaryKeySelective(OrderPackageImportLog record);

    int insert(OrderPackageImportLog record);

    int insertSelective(OrderPackageImportLog record);

    int insertByBatch(List<OrderPackageImportLog> list);

    List<OrderPackageImportLog> select(Page<OrderPackageImportLog> record);

    long count(Page<OrderPackageImportLog> record);

}
