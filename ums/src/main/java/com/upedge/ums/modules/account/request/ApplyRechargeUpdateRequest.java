package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ApplyRechargeUpdateRequest {

    @NotBlank
    private String transferFlow;
    @NotBlank
    private String receivingAccount;
    @NotNull
    private BigDecimal amount;


}
