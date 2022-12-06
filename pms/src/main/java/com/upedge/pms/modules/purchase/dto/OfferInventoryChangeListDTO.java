package com.upedge.pms.modules.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OfferInventoryChangeListDTO {

    @JsonProperty("offerId")
    private String offerId;
    @JsonProperty("offerOnSale")
    private Integer offerOnSale;
    @JsonProperty("skuId")
    private String skuId;
    @JsonProperty("skuOnSale")
    private Integer skuOnSale;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("bizTime")
    private String bizTime;
}
