package com.upedge.oms.modules.pack.dao;

import com.upedge.oms.modules.pack.entity.OrderPackage;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface OrderPackageDao{

    OrderPackage selectByPrimaryKey(OrderPackage record);

    int deleteByPrimaryKey(OrderPackage record);

    int updateByPrimaryKey(OrderPackage record);

    int updateByPrimaryKeySelective(OrderPackage record);

    int insert(OrderPackage record);

    int insertSelective(OrderPackage record);

    int insertByBatch(List<OrderPackage> list);

    List<OrderPackage> select(Page<OrderPackage> record);

    long count(Page<OrderPackage> record);

}
