package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderRefundItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderRefundItemDao{

    OrderRefundItem selectByPrimaryKey(OrderRefundItem record);

    int deleteByPrimaryKey(OrderRefundItem record);

    int updateByPrimaryKey(OrderRefundItem record);

    int updateByPrimaryKeySelective(OrderRefundItem record);

    int insert(OrderRefundItem record);

    int insertSelective(OrderRefundItem record);

    int insertByBatch(List<OrderRefundItem> list);

    List<OrderRefundItem> select(Page<OrderRefundItem> record);

    long count(Page<OrderRefundItem> record);

    List<OrderRefundItem> selectOrderRefundItemListbByRefundId(@Param("refundId") Long refundId);
}
