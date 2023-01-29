package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderItemUpdateVariantRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private Long variantId;

}
