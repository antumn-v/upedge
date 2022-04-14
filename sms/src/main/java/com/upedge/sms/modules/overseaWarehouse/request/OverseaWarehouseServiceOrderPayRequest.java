package com.upedge.sms.modules.overseaWarehouse.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OverseaWarehouseServiceOrderPayRequest {

    private Long orderId;

    Integer shipType;

    BigDecimal shipPrice;

    BigDecimal payAmount;


}
