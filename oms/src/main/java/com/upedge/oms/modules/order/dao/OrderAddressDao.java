package com.upedge.oms.modules.order.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.vo.SameAddressOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface OrderAddressDao{

    /**
     * 获取同一客户地址相同的订单
     * @param customerId
     * @return
     */
    List<SameAddressOrderVo> selectSameAddressOrderByStore(Long customerId);

    List<SameAddressOrderVo> selectSameAddressByOrderIds(@Param("orderIds") List<Long> orderIds);

    OrderAddress selectByOrderId(Long orderId);

    OrderAddress selectByPrimaryKey(Long id);

    int deleteByOrderIds(@Param("orderIds") List<Long> orderIds);

    int deleteByPrimaryKey(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);

    int updateByPrimaryKeySelective(OrderAddress record);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    int insertByBatch(List<OrderAddress> list);

    List<OrderAddress> select(Page<OrderAddress> record);

    long count(Page<OrderAddress> record);

    OrderAddress queryOrderAddressByOrderId(Long orderId);
}
