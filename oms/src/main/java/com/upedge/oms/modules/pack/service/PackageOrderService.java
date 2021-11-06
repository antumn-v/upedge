package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageOrder;

import java.util.List;

/**
 * @author author
 */
public interface PackageOrderService{

    PackageOrder selectByPrimaryKey(String clientOrderCode);

    int deleteByPrimaryKey(String clientOrderCode);

    int updateByPrimaryKey(PackageOrder record);

    int updateByPrimaryKeySelective(PackageOrder record);

    int insert(PackageOrder record);

    int insertSelective(PackageOrder record);

    List<PackageOrder> select(Page<PackageOrder> record);

    long count(Page<PackageOrder> record);
}

