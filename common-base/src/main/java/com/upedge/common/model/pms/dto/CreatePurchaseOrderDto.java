package com.upedge.common.model.pms.dto;

import lombok.Data;

@Data
public class CreatePurchaseOrderDto {


    private Long variantId;

    private Integer quantity;

    private Integer requireQuantity;
}
