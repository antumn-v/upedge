package com.upedge.oms.modules.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderTransactionDto {

    Long paymentId;

    Integer payMethod;

    BigDecimal fixFeePercentage;

    Date payTime;

    BigDecimal cnyRate;
}
