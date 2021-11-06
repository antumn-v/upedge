package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateSaiheSkuRequest {

    @NotBlank
    private String variantSku;
    @NotBlank
    private String saiheSku;

}
