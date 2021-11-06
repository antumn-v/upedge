package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageOrder;

import java.util.List;

/**
 * @author author
 */
public interface PackageOrderDao{

    PackageOrder selectByPrimaryKey(PackageOrder record);

    int deleteByPrimaryKey(PackageOrder record);

    int updateByPrimaryKey(PackageOrder record);

    int updateByPrimaryKeySelective(PackageOrder record);

    int insert(PackageOrder record);

    int insertSelective(PackageOrder record);

    int insertByBatch(List<PackageOrder> list);

    List<PackageOrder> select(Page<PackageOrder> record);

    long count(Page<PackageOrder> record);

    void savePackageOrder(List<PackageOrder> packageOrderList);
}
