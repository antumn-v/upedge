package com.upedge.pms.modules.quote.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuoteProductImportCartRequest {


    @NotNull
    private Long storeVariantId;

    @NotNull
    Integer quantity;
}
