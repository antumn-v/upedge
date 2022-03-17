package com.upedge.pms.modules.product.request;

import lombok.Data;

@Data
public class StoreSplitVariantUpdateRequest {

    Long storeVariantId;

    String name;

    String image;

    String imageBase64;
}
