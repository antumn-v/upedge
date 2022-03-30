package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateReshipOrderRequest {

    @NotNull(message = "没有选择订单")
    Long orderId;

    @NotNull(message = "未选择是否需要支付")
    Boolean needPay;

    Long shipMethodId;

    BigDecimal shipPrice;

    String reshipReason;

    @Min(value = 1,message = "订单里最少一个产品")
    List<Long> itemIds;
}
