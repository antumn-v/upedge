package com.upedge.common.model.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderStatisticsVo {

    String payTime;

    BigDecimal creditAmount;

    BigDecimal payAmount;

    Integer orderPayCount;

    BigDecimal refundAmount;

    BigDecimal shipPrice;

    BigDecimal productAmount;

    Integer refundCount;

    private Integer shippedRefundCount;

    private BigDecimal shippedRefundAmount;

    private Integer unShippedRefundCount;

    private BigDecimal unShippedRefundAmount;

    private BigDecimal benefitPayAmount;

}
