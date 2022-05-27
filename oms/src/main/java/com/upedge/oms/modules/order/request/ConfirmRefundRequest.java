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

    @NotNull
    private BigDecimal actualRefundAmount;


    private BigDecimal refundShipPrice;

    private BigDecimal refundVatAmount;

    private List<OrderRefundItem> refundItems;
}
