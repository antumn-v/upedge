package com.upedge.common.model.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreProductSalesVo {

    private Long storeProductId;
    /**
     *
     */
    private Long storeId;

    private Long orgId;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private BigDecimal one = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal seven = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal fifteen = BigDecimal.ZERO;
    /**
     *
     */
    private double dailyAverage;


    public double initDailyAverage() {
        this.dailyAverage = this.one.multiply(new BigDecimal("0.3"))
                .add(this.seven.divide(new BigDecimal("7"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("0.5")))
                .add(this.fifteen.divide(new BigDecimal("15"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("0.2")))
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return this.dailyAverage;
    }
}
