package com.upedge.ums.modules.affiliate.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AffiliateWithdrawalConfirmRequest {

    @NotBlank
    String paymentAccount;
}
