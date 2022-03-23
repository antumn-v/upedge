package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StoreSplitVariantUpdateRequest {

    @NotNull
    Long storeVariantId;

    String name;

    String image;

    String imageBase64;
}
