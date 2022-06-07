package com.upedge.sms.modules.center.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.center.entity.ServiceOrder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gx
 */
public interface ServiceOrderService{

    BaseResponse cancelOrder(Long id, Session session);

    BaseResponse orderInvoice(Long id);

    int updateToPaidByRelateId(Long relateId, Integer serviceType, BigDecimal payAmount, Date updateTime);

    ServiceOrder selectByRelateId(Long relateId, Integer serviceType);

    ServiceOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ServiceOrder record);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    List<ServiceOrder> select(Page<ServiceOrder> record);

    long count(Page<ServiceOrder> record);
}

