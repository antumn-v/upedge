package com.upedge.common.model.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentAmount {

    private Long paymentId;

    private BigDecimal payAmount = BigDecimal.ZERO;

    private BigDecimal productAmount = BigDecimal.ZERO;

    private BigDecimal shippingPrice = BigDecimal.ZERO;

    private BigDecimal vatAmount = BigDecimal.ZERO;

    private BigDecimal fixFee = BigDecimal.ZERO;

    private BigDecimal dischargeAmount = BigDecimal.ZERO;
}
