package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ConfirmRefundRequest {
    @NotNull
    private Long id;

    @NotNull
    private BigDecimal actualRefundAmount;
}
