package com.upedge.oms.modules.orderShippingUnit.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xwCui
 */
public interface OrderShippingUnitDao {

    List<Long> selectOrderIdByOrderPaymentId(@Param("paymentId") Long paymentId,
                                             @Param("orderType") Integer orderType);

    OrderShippingUnit selectByPrimaryKey(OrderShippingUnit record);

    int deleteUnPaidOrderUnitByShipUnitId(Long shipUnitId);

    int deleteByPrimaryKey(OrderShippingUnit record);

    int updateByPrimaryKey(OrderShippingUnit record);

    int updateByPrimaryKeySelective(OrderShippingUnit record);

    int insert(OrderShippingUnit record);

    int insertSelective(OrderShippingUnit record);

    int insertByBatch(List<OrderShippingUnit> list);

    List<OrderShippingUnit> select(Page<OrderShippingUnit> record);

    long count(Page<OrderShippingUnit> record);

    OrderShippingUnitVo selectByOrderId(@Param("orderId") Long orderId, @Param("orderType") int orderType);

    List<OrderShippingUnit> selectListByOrderId(@Param("shipUnitId") Long shipUnitId);

    int deleteOrderShippingUnit(@Param("id") Long id, @Param("orderType") int orderType, @Param("orderId") Long orderId);

    int delete(@Param("record") OrderShippingUnit record);

    int deleteByOrderIds(@Param("orderIds") List<Long> orderIds, @Param("orderType") Integer orderType);

    void delByProductId(@Param("productId") Long productId, @Param("orderType") int orderType);

    void delByVariantId(@Param("variantId") Long variantId, @Param("orderType") int orderType);
}
