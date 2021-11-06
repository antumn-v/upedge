package com.upedge.common.model.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AccountOrderRefundedRequest {

    @NotNull
    Long customerId;
    @NotNull
    Long orderId;
    @NotNull
    Integer orderType;
    @NotNull
    Long refundId;

    /**
     * 退款金额
     */
    @NotNull
    BigDecimal refundAmount;
    /**
     *运费+产品费+VAT税费
     */
    @NotNull
    BigDecimal payAmount;



}
