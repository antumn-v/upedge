package com.upedge.common.utils;

import java.math.BigDecimal;

public class PriceUtils {

    public static BigDecimal defaultEurRate = new BigDecimal("1.11");

    private static BigDecimal defaultUsdRate = new BigDecimal("6.3");

    public static BigDecimal defaultProfitMargin = new BigDecimal("1.2");

    public static BigDecimal cnyToUsd(BigDecimal cnyPrice, BigDecimal usdRate){
        return cnyPrice
                .divide(usdRate,2, BigDecimal.ROUND_HALF_UP)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal cnyToUsdByDefaultRate(BigDecimal cnyPrice){
        return cnyPrice
                .divide(defaultUsdRate,2, BigDecimal.ROUND_UP)
                .setScale(2, BigDecimal.ROUND_UP);
    }

    public static BigDecimal eurToUsdByDefaultRate(BigDecimal eurPrice){
        return eurPrice.multiply(defaultEurRate)
                .setScale(2, BigDecimal.ROUND_UP);
    }

}
