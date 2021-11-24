package com.upedge.common.model.pms.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerProductQuoteSearchRequest {

    private Long quoteApplyId;

    private Long storeVariantId;

    private Long storeProductId;

    private List<Long> storeProductVariantIds;

    private List<Long> storeProductProductIds;
}
