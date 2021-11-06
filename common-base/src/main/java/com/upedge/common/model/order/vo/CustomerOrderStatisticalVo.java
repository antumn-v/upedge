package com.upedge.common.model.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerOrderStatisticalVo {

    /**
     *  订单总金额  orderAllMoney
     *  订单总数 totalPandaPaidOrderNum;
     *  取消订单数  cancelOrderCount
     *   支付且发货的订单数  PayAndShipOrderCount
     *   已支付未发货订单数  PayButNoShipOrderCount
     *   已完成订单数    completeOrderCount
     *   待支付订单数  noPayOrderCount
     */
    private BigDecimal orderAllMoney = BigDecimal.ZERO;
    private Integer totalPandaPaidOrderNum = 0;
    private Integer cancelOrderCount;
    private Integer payAndShipOrderCount;
    private Integer payButNoShipOrderCount;
    private Integer completeOrderCount;
    private Integer noPayOrderCount;
}
