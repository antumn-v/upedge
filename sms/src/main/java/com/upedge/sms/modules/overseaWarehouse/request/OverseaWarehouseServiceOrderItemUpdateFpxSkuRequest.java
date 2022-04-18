package com.upedge.sms.modules.overseaWarehouse.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OverseaWarehouseServiceOrderItemUpdateFpxSkuRequest {

    @NotNull
    Long itemId;

    @NotNull
    String warehouseSku;

    Boolean otherOrderValid = true;
}
