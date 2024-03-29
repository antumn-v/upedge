package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackageBackup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPackageBackupDao {

    List<OrderPackageBackup> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    OrderPackageBackup selectByPrimaryKey(OrderPackageBackup record);

    int deleteByPrimaryKey(OrderPackageBackup record);

    int updateByPrimaryKey(OrderPackageBackup record);

    int updateByPrimaryKeySelective(OrderPackageBackup record);

    int insert(OrderPackageBackup record);

    int insertSelective(OrderPackageBackup record);

    int insertByBatch(List<OrderPackageBackup> list);

    List<OrderPackageBackup> select(Page<OrderPackageBackup> record);

    long count(Page<OrderPackageBackup> record);

}
