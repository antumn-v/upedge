package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YunExpressGetTrackNumDto  {


    @JsonProperty("OrderNumber")
    private String orderNumber;
    @JsonProperty("CustomerOrderNumber")
    private String customerOrderNumber;
    @JsonProperty("TrackingNumber")
    private String trackingNumber;
    @JsonProperty("WayBillNumber")
    private String wayBillNumber;
    @JsonProperty("ChildTrackings")
    private List<ChildTrackingsDTO> childTrackings;
    @JsonProperty("Remark")
    private String remark;
    @JsonProperty("Status")
    private Integer status;

    @NoArgsConstructor
    @Data
    public static class ChildTrackingsDTO {
        @JsonProperty("ChildOrderNumber")
        private String childOrderNumber;
        @JsonProperty("ChildTrackingNumber")
        private Object childTrackingNumber;
    }
}
