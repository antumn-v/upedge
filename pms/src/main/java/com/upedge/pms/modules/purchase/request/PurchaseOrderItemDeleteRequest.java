package com.upedge.pms.modules.purchase.request;

import lombok.Data;

@Data
public class PurchaseOrderItemDeleteRequest {

    private Long orderId;

    private Long itemId;
}
