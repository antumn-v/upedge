package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class InvoiceDetailRequest {

    Long paymentId;

    private List<Long> paymentIds;

    @NotNull
    Integer orderType;
}
