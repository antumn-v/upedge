package com.upedge.common.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountPaymentRequest {

    private Long id;
    /**
     *
     */
    private Long userId;

    private Long accountId;

    /**
     *
     */
    private Long customerId;
    /**
     * 0=备库订单 1=普通订单 2=批发订单
     */
    private Integer orderType;
    /**
     *
     */
    private BigDecimal payAmount;
    /**
     * 手续费
     */
    private BigDecimal fixFee;

    /**
     * 支付方式：0=recharge 1=paypal
     */
    private Integer payType;


}
