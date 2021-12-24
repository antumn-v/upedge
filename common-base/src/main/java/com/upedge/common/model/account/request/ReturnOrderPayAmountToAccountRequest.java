package com.upedge.common.model.account.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReturnOrderPayAmountToAccountRequest {

    Long paymentId;

    Long orderId;

    BigDecimal payAmount;
}
