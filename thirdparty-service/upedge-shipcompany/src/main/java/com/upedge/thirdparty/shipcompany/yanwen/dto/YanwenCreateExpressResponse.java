package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class YanwenCreateExpressResponse {


    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("code")
    private String code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private YanwenCreateExpressResponseDataDTO data;

    @NoArgsConstructor
    @Data
    public static class YanwenCreateExpressResponseDataDTO {
        @JsonProperty("waybillNumber")
        private String waybillNumber;
        @JsonProperty("orderNumber")
        private String orderNumber;
        @JsonProperty("referenceNumber")
        private String referenceNumber;
        @JsonProperty("yanwenNumber")
        private String yanwenNumber;
    }
}
