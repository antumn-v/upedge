package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WayBillDeleteDto {


    @JsonProperty("Item")
    private WayBillDeleteItemDTO item;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("TimeStamp")
    private String timeStamp;

    @NoArgsConstructor
    @Data
    public static class WayBillDeleteItemDTO {
        @JsonProperty("OrderNumber")
        private String orderNumber;
        @JsonProperty("Status")
        private String status;
        @JsonProperty("Type")
        private Integer type;
        @JsonProperty("Remark")
        private String remark;
    }
}
