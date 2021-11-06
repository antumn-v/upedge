package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderPayRequest {
    @Size(min = 1)
    List<Long> orderIds;

    @NotNull
    BigDecimal amount;
}
