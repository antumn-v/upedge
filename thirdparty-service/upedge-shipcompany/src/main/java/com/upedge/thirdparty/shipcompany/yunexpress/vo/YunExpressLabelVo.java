package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YunExpressLabelVo {

    @JsonProperty("Url")
    private String url;


    @JsonProperty("OrderInfos")
    private List<OrderInfosDTO> orderInfos;

    @NoArgsConstructor
    @Data
    public static class OrderInfosDTO {
        @JsonProperty("CustomerOrderNumber")
        private String customerOrderNumber;
        @JsonProperty("Error")
        private String error;
        @JsonProperty("Code")
        private Integer code;
    }
}
