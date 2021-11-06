package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by cjq on 2019/5/22.
 */
@Data
public class SaleTotalVo {

    /**
     * orderSourceName
     */
    String orderSourceName;

    /**
     * 时间区间、间隔
     */
    String timeDate;

    //当月包裹订单总数
    Integer countPackageOrder;

    //当月发货包裹订单销售额
    BigDecimal totalPackageOrderAsales;

    //当月发货包裹订单采购成本
    BigDecimal totalPackagePurchaseCost;

    //当月补发包裹采购成本
    BigDecimal againOrderPurchaseCost;

    //当月补发包裹物流成本
    BigDecimal againOrderLogisticsCost;

    //当月发货包裹订单物流成本
    BigDecimal totalPackageLogisticsCost;

    //当月发货包裹订单返点
    BigDecimal totalPackageOrderBenefits;

    //当月确认已发货订单退款金额
//    BigDecimal monthRefundTrackingYesAmount;
    BigDecimal monthRefundTrackingYesAmountNew;

//    BigDecimal packageOrderRefundTrackingYesAmount;
//    BigDecimal packageOrderRefundTrackingYesAmountNew;
//    BigDecimal wholesaleOrderRefundTrackingYesAmount;
    BigDecimal wholesaleOrderRefundTrackingYesAmountNew;

    //当月确认未发货订单退款金额
    BigDecimal monthRefundTrackingNoAmount;

    //当月利润
    BigDecimal totalPackageProfit;

    //当月美元汇款
    BigDecimal usdRate;

}
