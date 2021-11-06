package com.upedge.common.model.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class PaypalPayment {

    /**
     *
     */
    @NotNull
    private Long id;
    /**
     * 收款方交易号
     */
    private String saleId;

    private String token;
    /**
     *
     */
    private String paymentId;
    /**
     *
     */
    private String paymentMethod;
    /**
     *
     */
    private String payerEmail;
    /**
     *
     */
    private String payerName;
    /**
     *
     */
    private String payerId;
    /**
     *
     */
    @NotBlank
    private String state;
    /**
     *
     */
    private String amount;
    /**
     *
     */
    private String currency;
    /**
     *
     */
    private String fixFee;
    /**
     *
     */
    private String createTime;
    /**
     *
     */
    private String updateTime;
    /**
     *
     */
    private String payeeEmail;
    /**
     *
     */
    private String merchantId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 处理人
     */
    private Integer accountPaymethodId;
    /**
     *
     */
    private Long accountId;
    /**
     *
     */
    private Integer requestId;

    @NotNull
    private Integer orderType;

    private Long customerId;

    private Long userId;

}
