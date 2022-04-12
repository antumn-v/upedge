package com.upedge.sms.modules.center.service;

import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface ServiceOrderService{

    ServiceOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ServiceOrder record);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    List<ServiceOrder> select(Page<ServiceOrder> record);

    long count(Page<ServiceOrder> record);
}

