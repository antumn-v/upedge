package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProfitVo {

    private BigDecimal storeOrderIncome;

    private BigDecimal orderProductCost;

    private BigDecimal orderShipCost;

    private BigDecimal storeOrderRefund;

    private BigDecimal orderRefund;

    private BigDecimal profit;

    public BigDecimal initProfit(){
        this.profit = this.storeOrderIncome.subtract(this.orderProductCost).subtract(this.orderShipCost).subtract(this.storeOrderRefund).add(this.orderRefund);
        return this.profit;
    }
}
