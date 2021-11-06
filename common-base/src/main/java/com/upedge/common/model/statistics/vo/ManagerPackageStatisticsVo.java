package com.upedge.common.model.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerPackageStatisticsVo {

    private String managerCode;
    /**
     * 包裹总数
     */
    private Integer packageCount;
    /**
     * 包裹订单总数
     */
    private Integer packageOrderCount;
    /**
     * 包裹采购总成本
     */
    private BigDecimal purchaseCostAmount;
    /**
     * 包裹物流总成本
     */
    private BigDecimal shippingFeeAmount;
    /**
     * 包裹订单总销售额
     */
    private BigDecimal packageOrderAmount;
    /**
     * 支付到发货的处理总时长(秒，同下)
     */
    private Long handleTimeAmount;
    /**
     * 从包裹生成到发货的处理总时长
     */
    private Long packageShipDurationAmount;
    /**
     * 从支付到包裹生成的处理总时长
     */
    private Long payPackageDurationAmount;
    /**
     * 来源渠道ID
     */
    private Integer orderSourceId;
    /**
     * 来源渠道名称
     */
    private String orderSourceName;
    /**
     * 来源渠道类型（枚举）
     */
    private Integer orderSourceType;
}
