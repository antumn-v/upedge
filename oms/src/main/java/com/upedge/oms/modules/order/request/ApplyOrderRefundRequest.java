package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderRefundItem;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApplyOrderRefundRequest {
    @NotNull
    private Long orderId;
    @NotBlank
    private String refundReason;
    @NotNull
    BigDecimal shippingPrice;
    @NotNull
    BigDecimal vatAmount;

    String remark;

    boolean directRefund;

    List<OrderRefundItem> refundItemList=new ArrayList<>();


}
