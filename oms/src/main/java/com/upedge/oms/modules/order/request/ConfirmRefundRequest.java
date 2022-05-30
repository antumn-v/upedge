package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderRefundItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ConfirmRefundRequest {
    @NotNull
    private Long id;

    private BigDecimal actualRefundAmount;
    @NotNull
    private BigDecimal serviceFee;
    @NotNull
    private BigDecimal refundShipPrice;
    @NotNull
    private BigDecimal refundVatAmount;

    private List<OrderRefundItem> refundItems;
}
