package com.upedge.oms.modules.stock.vo;

import com.upedge.oms.modules.stock.entity.StockAdviceSetting;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerStockAdviceVo {

    Long customerId;

    Long productId;

    Long variantId;

    BigDecimal price;

    String variantName;

    String variantSku;

    String variantImage;

    String productTitle;

    BigDecimal five = BigDecimal.ZERO;

    BigDecimal ten = BigDecimal.ZERO;

    BigDecimal fifteen = BigDecimal.ZERO;

    BigDecimal dailyAverage;

    Integer stockAdvice;

    Integer stockDays = 7;

    Integer stock;

    public double initDailyAverage(StockAdviceSetting setting) {

        this.stockDays = setting.getStockDays();
        this.dailyAverage = (this.five.divide(new BigDecimal(5),2, BigDecimal.ROUND_HALF_UP).multiply(setting.getFactorA()))
                .add(this.ten.divide(new BigDecimal(10),2, BigDecimal.ROUND_HALF_UP).multiply(setting.getFactorB()))
                .add(this.fifteen.divide(new BigDecimal(15),2, BigDecimal.ROUND_HALF_UP).multiply(setting.getFactorC()))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.stockAdvice = this.dailyAverage.multiply(new BigDecimal(this.stockDays)).intValue();
        return this.dailyAverage.doubleValue();
    }

    public Integer initStockAdvice(Integer stock) {
        if (null == stock) {
            stock = 0;
        }
        this.stock = stock;
        int j = this.dailyAverage.multiply(new BigDecimal(this.stockDays)).intValue() - stock;
        if (j < 0) {
            j = 0;
        }
        this.stockAdvice = j;
        return j;
    }


}
