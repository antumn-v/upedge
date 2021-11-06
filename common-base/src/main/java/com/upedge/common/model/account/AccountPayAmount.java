package com.upedge.common.model.account;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class AccountPayAmount {

    private BigDecimal amount;

    private Long accountId;

    private Integer accountPaymethodId;

    private String remarks;
}
