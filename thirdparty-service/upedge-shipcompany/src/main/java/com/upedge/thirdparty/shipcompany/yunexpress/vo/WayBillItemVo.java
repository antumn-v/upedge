package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
public class WayBillItemVo {

    @JsonProperty("CustomerOrderNumber")
    private String customerOrderNumber;
    @JsonProperty("Success")
    private Integer success;
    @JsonProperty("TrackType")
    private String trackType;
    @JsonProperty("Remark")
    private String remark;
    @JsonProperty("AgentNumber")
    private String agentNumber;
    @JsonProperty("WayBillNumber")
    private String wayBillNumber;
    @JsonProperty("RequireSenderAddress")
    private Integer requireSenderAddress;
    @JsonProperty("TrackingNumber")
    private String trackingNumber;
    @JsonProperty("ShipperBoxs")
    private List<ShipperBoxsDTO> shipperBoxs;

    @NoArgsConstructor
    @Data
    public static class ShipperBoxsDTO {
        @JsonProperty("BoxNumber")
        private String boxNumber;
        @JsonProperty("ShipperHawbcode")
        private String shipperHawbcode;
    }
}
