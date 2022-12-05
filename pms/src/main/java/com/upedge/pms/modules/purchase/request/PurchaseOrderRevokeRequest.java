package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PurchaseOrderRevokeRequest {

    @NotNull
    private Long orderId;

    private String cancelReason;

    private String remark;
}
