package com.upedge.oms.modules.orderShippingUnit.request;

import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class OrderShippingUnitUpdateRequest{

    /**
     * 运输单元id
     */
    private Long shipUnitId;
    /**
     * 运输方式id
     */
    private Long methodId;
    /**
     * 
     */
    private String methodName;
    /**
     * 始发地id
     */
    private String fromAreaId;
    /**
     * 目的地id
     */
    private String toAreaId;
    /**
     * 区间开始重量
     */
    private BigDecimal startWeight;
    /**
     * 区间结束重量
     */
    private BigDecimal endWeight;
    /**
     * 固定费+挂号费
     */
    private BigDecimal fixedFee;
    /**
     * 各重费
     */
    private BigDecimal weightCharge;
    /**
     * 预计到达时间
     */
    private Integer deliveryMinDay;
    /**
     * 
     */
    private Integer deliveryMaxDay;
    /**
     * 折扣
     */
    private BigDecimal discount;
    /**
     * 
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 0:禁用 1:启用
     */
    private Integer state;
    /**
     * 订单id
     */
    private Long orderId;
    /**
     *  订单类型   2 普通  3 批发
     */
    private Integer orderType;

    public OrderShippingUnit toOrderShippingUnit(Long id){
        OrderShippingUnit orderShippingUnit=new OrderShippingUnit();
        orderShippingUnit.setId(id);
        orderShippingUnit.setMethodId(methodId);
        orderShippingUnit.setMethodName(methodName);
        orderShippingUnit.setFromAreaId(fromAreaId);
        orderShippingUnit.setToAreaId(toAreaId);
        orderShippingUnit.setStartWeight(startWeight);
        orderShippingUnit.setEndWeight(endWeight);
        orderShippingUnit.setFixedFee(fixedFee);
        orderShippingUnit.setWeightCharge(weightCharge);
        orderShippingUnit.setDeliveryMinDay(deliveryMinDay);
        orderShippingUnit.setDeliveryMaxDay(deliveryMaxDay);
        orderShippingUnit.setDiscount(discount);
        orderShippingUnit.setRemarks(remarks);
        orderShippingUnit.setCreateTime(createTime);
        orderShippingUnit.setState(state);
        orderShippingUnit.setOrderId(orderId);
        orderShippingUnit.setOrderType(orderType);
        return orderShippingUnit;
    }

}
