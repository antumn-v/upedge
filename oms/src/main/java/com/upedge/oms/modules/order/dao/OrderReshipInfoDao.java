package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderReshipInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderReshipInfoDao{

    OrderReshipInfo selectByPrimaryKey(Long orderId);

    int deleteByPrimaryKey(OrderReshipInfo record);

    int updateByPrimaryKey(OrderReshipInfo record);

    int updateByPrimaryKeySelective(OrderReshipInfo record);

    int insert(OrderReshipInfo record);

    int insertSelective(OrderReshipInfo record);

    int insertByBatch(List<OrderReshipInfo> list);

    List<OrderReshipInfo> select(Page<OrderReshipInfo> record);

    long count(Page<OrderReshipInfo> record);

    void updateReshipTimes(@Param("ids") List<Long> ids, @Param("reshipTimes") int reshipTimes);

    List<OrderReshipInfo> listOrderReshipInfoByIds(@Param("ids") List<Long> reshipIds);

}
