package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.PaypalCarriers;

import java.util.List;

/**
 * @author author
 */
public interface PaypalCarriersDao{

    PaypalCarriers selectByPrimaryKey(PaypalCarriers record);

    int deleteByPrimaryKey(PaypalCarriers record);

    int updateByPrimaryKey(PaypalCarriers record);

    int updateByPrimaryKeySelective(PaypalCarriers record);

    int insert(PaypalCarriers record);

    int insertSelective(PaypalCarriers record);

    int insertByBatch(List<PaypalCarriers> list);

    List<PaypalCarriers> select(Page<PaypalCarriers> record);

    long count(Page<PaypalCarriers> record);

    List<PaypalCarriers> allPaypalCarriers();
}
