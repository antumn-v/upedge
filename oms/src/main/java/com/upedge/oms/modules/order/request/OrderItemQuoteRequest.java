package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderItemQuoteRequest {
    @NotNull
    Long orderId;

    List<Long> itemIds;
}
