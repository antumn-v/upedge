package com.upedge.oms.modules.order.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface OrderRefundItemService{

    List<OrderRefundItem> selectByRefundId(Long refundId);

    int updateByPrimaryKey(OrderRefundItem record);

    int updateByPrimaryKeySelective(OrderRefundItem record);

    int insert(OrderRefundItem record);

    int insertSelective(OrderRefundItem record);

    List<OrderRefundItem> select(Page<OrderRefundItem> record);

    long count(Page<OrderRefundItem> record);

    List<OrderRefundItem> selectOrderRefundItemListbByRefundId(Long refundId);
}

