package com.upedge.common.model.oms;

import lombok.Data;

@Data
public class WholesaleOrderItemLockStockRequest {

    private Long variantId;

    private Long customerId;

    private Integer dischargeQuantity;

    private Integer originalDischargeQuantity;
}
