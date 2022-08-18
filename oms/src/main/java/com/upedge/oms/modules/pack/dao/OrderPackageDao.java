package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPackageDao {

    List<OrderPackage> selectByIds(@Param("ids") List<Long> ids);

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
