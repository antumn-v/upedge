package com.upedge.pms.modules.quote.dto;

import lombok.Data;

@Data
public class AllCustomerQuoteProductSearchDto {

    private String storeVariantName;

    private String storeVariantSku;

    private Long customerId;

    private Long storeProductId;
}
