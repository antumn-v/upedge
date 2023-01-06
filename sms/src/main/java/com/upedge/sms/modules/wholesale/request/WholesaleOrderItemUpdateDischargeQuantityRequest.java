package com.upedge.sms.modules.wholesale.request;

import lombok.Data;

@Data
public class WholesaleOrderItemUpdateDischargeQuantityRequest {

    private Long orderId;

    private Long itemId;

    private Integer dischargeQuantity;
}
