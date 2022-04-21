package com.upedge.sms.modules.center.service;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;

import java.util.List;

/**
 * @author gx
 */
public interface ServiceOrderFreightService{

    ServiceOrderFreight selectByOrderIdAndShipType(Long orderId, Integer shipType,Integer serviceType);

    List<ServiceOrderFreight> selectByOrderId(Long orderId,Integer serviceType);

    ServiceOrderFreight selectByPrimaryKey(Long orderId);

    int deleteByPrimaryKey(Long orderId);

    int updateByPrimaryKey(ServiceOrderFreight record);

    int updateByPrimaryKeySelective(ServiceOrderFreight record);

    int insert(ServiceOrderFreight record);

    int insertByBatch(List<ServiceOrderFreight> records);

    int insertSelective(ServiceOrderFreight record);

    List<ServiceOrderFreight> select(Page<ServiceOrderFreight> record);

    long count(Page<ServiceOrderFreight> record);
}

