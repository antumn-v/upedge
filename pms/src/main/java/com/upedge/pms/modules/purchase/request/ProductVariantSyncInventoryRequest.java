package com.upedge.pms.modules.purchase.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.pms.modules.purchase.dto.OfferInventoryChangeListDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ProductVariantSyncInventoryRequest {


    @JsonProperty("data")
    private DataDTO data;
    @JsonProperty("gmtBorn")
    private Long gmtBorn;
    @JsonProperty("msgId")
    private Long msgId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("userInfo")
    private String userInfo;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("OfferInventoryChangeList")
        private List<OfferInventoryChangeListDTO> offerInventoryChangeList;
    }
}
