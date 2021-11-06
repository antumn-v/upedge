package com.upedge.common.model.product;

import lombok.Data;

@Data
public class StoreProductVariantVo {

    Long storeId;

    String platProductId;

    Long storeProductId;

    String platVariantId;

    Long storeVariantId;

    String sku;

    String image;

    String title;

    String name;
}
