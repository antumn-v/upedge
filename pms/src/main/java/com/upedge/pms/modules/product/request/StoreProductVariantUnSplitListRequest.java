package com.upedge.pms.modules.product.request;

import lombok.Data;

@Data
public class StoreProductVariantUnSplitListRequest {

    String variantName;

    String sku;

    Long customerId;
}
