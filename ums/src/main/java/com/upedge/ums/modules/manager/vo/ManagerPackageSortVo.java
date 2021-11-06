package com.upedge.ums.modules.manager.vo;

import com.upedge.common.model.statistics.vo.ManagerPackageStatisticsVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerPackageSortVo {

    private String managerCode;

    /**
     * 包裹数
     */
    private Integer packageCount = 0;

    private Integer packageOrderCount = 0;

    /**
     * 包裹订单总销售额
     */
    private BigDecimal packageOrderAmount = BigDecimal.ZERO;
    /**
     * 利润
     */
    private BigDecimal packageProfit = BigDecimal.ZERO;
    /**
     * 利润率
     */
    private BigDecimal packageProfitMargin = BigDecimal.ZERO;
    /**
     * 5天内发货订单占比
     */
    private BigDecimal fiveDayDeDeliveryRatio = BigDecimal.ZERO;

    /**
     * 平均订单发货时效
     */
    private String averageDeliveryTime = "0d0h";

    private BigDecimal usdRate = new BigDecimal("6.3");

    public ManagerPackageSortVo(ManagerPackageStatisticsVo packageStatisticsVo,
                                BigDecimal shippedRefundAmount,
                                BigDecimal benefitAmount,
                                Integer orderCount, BigDecimal usdRate) {
        this.usdRate = usdRate;
        this.packageOrderAmount = packageStatisticsVo.getPackageOrderAmount().multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);;
        this.packageCount = packageStatisticsVo.getPackageCount();
        this.managerCode = packageStatisticsVo.getManagerCode();
        this.packageProfit =
                packageStatisticsVo.getPackageOrderAmount()
                .subtract(packageStatisticsVo.getPurchaseCostAmount())
                .subtract(packageStatisticsVo.getShippingFeeAmount())
                .subtract(shippedRefundAmount)
                .subtract(benefitAmount).multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.packageProfitMargin = this.packageProfit.divide(this.packageOrderAmount,2, BigDecimal.ROUND_HALF_UP);
        this.fiveDayDeDeliveryRatio = new BigDecimal(100*orderCount /(this.packageCount*1.0)).setScale(2, BigDecimal.ROUND_HALF_UP);
        long avgDuration=0,avgDurationD=0,avgDurationH=0,avgDurationM=0;
        //平均时效
        if(orderCount!=0) {
            avgDuration=packageStatisticsVo.getHandleTimeAmount()/orderCount;
            long avgD=avgDuration;
            //天
            avgDurationD=avgDuration/24/3600;
            avgD=avgD-avgDurationD*3600*24;
            //小时
            avgDurationH=avgD/3600;
            avgD=avgD-avgDurationH*3600;
            //分
            avgDurationM=avgD/60;
            avgD=avgD-avgDurationM*60;
        }
        this.averageDeliveryTime=avgDurationD+"d"+avgDurationH+"h";
        this.packageOrderCount = packageCount;
    }

    public ManagerPackageSortVo() {
    }
}
