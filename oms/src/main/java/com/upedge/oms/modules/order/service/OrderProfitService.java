package com.upedge.oms.modules.order.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderProfit;
import com.upedge.oms.modules.order.vo.OrderProfitVo;

import java.util.List;

/**
 * @author author
 */
public interface OrderProfitService{

    OrderProfit updateOrderProfit(Long orderId);

    OrderProfitVo selectByPrimaryKey(Long orderId);

    int deleteByPrimaryKey(Long orderId);

    int updateByPrimaryKey(OrderProfit record);

    int updateByPrimaryKeySelective(OrderProfit record);

    int insert(OrderProfit record);

    int insertSelective(OrderProfit record);

    List<OrderProfit> select(Page<OrderProfit> record);

    long count(Page<OrderProfit> record);
}

