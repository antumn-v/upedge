package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Data
public class AccountBalanceWithdrawRequest {

    @NotNull
    Long customerId;

    @NotNull
    BigDecimal balance;

    @NotNull
    BigDecimal vipRebate;

    @NotNull
    BigDecimal affiliateRebate;


}
