package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.PaypalCarriers;
import com.upedge.tms.modules.ship.response.PaypalCarriersListResponse;

import java.util.List;

/**
 * @author author
 */
public interface PaypalCarriersService{

    PaypalCarriers selectByPrimaryKey(String enumeratedValue);

    int deleteByPrimaryKey(String enumeratedValue);

    int updateByPrimaryKey(PaypalCarriers record);

    int updateByPrimaryKeySelective(PaypalCarriers record);

    int insert(PaypalCarriers record);

    int insertSelective(PaypalCarriers record);

    List<PaypalCarriers> select(Page<PaypalCarriers> record);

    long count(Page<PaypalCarriers> record);

    PaypalCarriersListResponse allPaypalCarriers();
}

