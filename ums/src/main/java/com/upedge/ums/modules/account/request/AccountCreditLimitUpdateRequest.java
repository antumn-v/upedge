package com.upedge.ums.modules.account.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by guoxing on 2020/11/3.
 */
public class AccountCreditLimitUpdateRequest {
    @NotNull
    private BigDecimal creditLimit;

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }
}
