package com.upedge.sms.modules.center.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface ServiceOrderFreightDao {

    ServiceOrderFreight selectByOrderIdAndShipType(@Param("orderId") Long orderId, @Param("shipType") Integer shipType);

    List<ServiceOrderFreight> selectByOrderId(@Param("orderId") Long orderId);

    ServiceOrderFreight selectByPrimaryKey(ServiceOrderFreight record);

    int deleteByPrimaryKey(ServiceOrderFreight record);

    int updateByPrimaryKey(ServiceOrderFreight record);

    int updateByPrimaryKeySelective(ServiceOrderFreight record);

    int insert(ServiceOrderFreight record);

    int insertSelective(ServiceOrderFreight record);

    int insertByBatch(List<ServiceOrderFreight> list);

    List<ServiceOrderFreight> select(Page<ServiceOrderFreight> record);

    long count(Page<ServiceOrderFreight> record);

}
