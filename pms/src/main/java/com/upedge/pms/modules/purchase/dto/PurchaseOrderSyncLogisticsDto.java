package com.upedge.pms.modules.purchase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PurchaseOrderSyncLogisticsDto {


    @JsonProperty("orderLogsItems")
    private List<PurchaseOrderLogsItemsDTO> orderLogsItems;

    @Data
    public static class PurchaseOrderLogsItemsDTO {
        @JsonProperty("orderId")
        private String orderId;
        @JsonProperty("orderEntryId")
        private String orderEntryId;
    }
}
