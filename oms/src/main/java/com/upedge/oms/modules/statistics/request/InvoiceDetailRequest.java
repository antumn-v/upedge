package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class InvoiceDetailRequest {

    @NotNull
    Long paymentId;

    @NotNull
    Integer orderType;
}
