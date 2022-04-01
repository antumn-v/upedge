package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    Long shipUnitId;

    String reshipReason;

    @Size(min = 1,message = "订单里最少一个产品")
    List<Long> itemIds;
}
