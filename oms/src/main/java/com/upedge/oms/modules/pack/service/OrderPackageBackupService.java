package com.upedge.oms.modules.pack.service;

import com.upedge.oms.modules.pack.entity.OrderPackageBackup;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPackageBackupService{

    List<OrderPackageBackup> selectByOrderIds(List<Long> orderIds);

    OrderPackageBackup selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderPackageBackup record);

    int updateByPrimaryKeySelective(OrderPackageBackup record);

    int insert(OrderPackageBackup record);

    int insertSelective(OrderPackageBackup record);

    List<OrderPackageBackup> select(Page<OrderPackageBackup> record);

    long count(Page<OrderPackageBackup> record);
}

