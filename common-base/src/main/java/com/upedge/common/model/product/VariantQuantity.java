package com.upedge.common.model.product;

import lombok.Data;

@Data
public class VariantQuantity {

    Long variantId;

    String variantSku;

    Integer quantity;

}
