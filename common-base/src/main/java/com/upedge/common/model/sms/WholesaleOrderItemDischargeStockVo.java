package com.upedge.common.model.sms;

import lombok.Data;

@Data
public class WholesaleOrderItemDischargeStockVo {

    private Long id;

    private Long customerId;

    private Long variantId;

    private Integer dischargeQuantity;
}
