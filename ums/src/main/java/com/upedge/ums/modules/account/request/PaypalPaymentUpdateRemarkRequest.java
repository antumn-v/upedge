package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PaypalPaymentUpdateRemarkRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String remark;
}
