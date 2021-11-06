package com.upedge.ums.modules.affiliate.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AffiliateWithdrawalRequest {

    @NotNull
    private BigDecimal money;

    @NotNull
    private String account;

    //0 = SourcinBox,1=Paypal,2=Payoneer
    @NotNull
    private Integer type;

    private String remark;
}
