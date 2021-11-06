package com.upedge.common.model.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderStatisticsVo {

    BigDecimal creditAmount;

    BigDecimal payAmount;

    Integer orderPayCount;

    BigDecimal refundAmount;

    Integer refundCount;

    private Integer shippedRefundCount = 0;

    private BigDecimal shippedRefundAmount = BigDecimal.ZERO;

    private Integer unShippedRefundCount = 0;

    private BigDecimal unShippedRefundAmount = BigDecimal.ZERO;

    private BigDecimal benefitPayAmount = BigDecimal.ZERO;

}
