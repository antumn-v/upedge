package com.upedge.oms.modules.orderShippingUnit.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderShippingUnitVo {

    /**
     *
     */
    private Long id;

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
    private int orderType;

    /**
     * 订单支付状态
     */
    private int orderPayState;
}
