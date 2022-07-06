package com.upedge.oms.modules.orderShippingUnit.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;

import java.util.List;

public interface OrderShippingUnitService{

    List<Long> selectOrderIdByOrderPaymentId(Long paymentId,
                                             Integer orderType);

    void updateOrderShipUnit(Long orderId, Long shippingUnitId);


    void deleteByShipUnitId(Long shipUnitId);

    OrderShippingUnit selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderShippingUnit record);

    int updateByPrimaryKeySelective(OrderShippingUnit record);

    int insert(OrderShippingUnit record);

    int insertSelective(OrderShippingUnit record);

    List<OrderShippingUnit> select(Page<OrderShippingUnit> record);

    long count(Page<OrderShippingUnit> record);
    /**
     * 查询带订单支付状态的订单unit信息
     * @param orderId
     * @return
     */
    OrderShippingUnitVo selectByOrderId(Long orderId, int orderType);

    /**
     * 根据订单id和类型删除unit信息
     * @param orderId
     * @return
     */
    int delByOrderId(Long orderId, int orderType);


//    void delOrderShipUnitAndShipMethod(List<Long> longs);

    void deleteByOrderIds(List<Long> orderIds,Integer orderType);
}

