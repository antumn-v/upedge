package com.upedge.sms.modules.overseaWarehouse.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class OverseaWarehouseServiceOrderReceiptRequest {

    @NotNull
    Long orderId;

    @NotNull
    String trackingCode;
}
