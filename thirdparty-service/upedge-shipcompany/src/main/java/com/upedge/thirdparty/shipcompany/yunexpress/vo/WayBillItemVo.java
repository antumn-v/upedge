package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
public class WayBillItemVo {

    @JSONField(name = "CustomerOrderNumber")
    private String customerOrderNumber;
    @JSONField(name = "Success")
    private Integer success;
    @JSONField(name = "TrackType")
    private String trackType;
    @JSONField(name = "Remark")
    private String remark;
    @JSONField(name = "AgentNumber")
    private String agentNumber;
    @JSONField(name = "WayBillNumber")
    private String wayBillNumber;
    @JSONField(name = "RequireSenderAddress")
    private Integer requireSenderAddress;
    @JSONField(name = "TrackingNumber")
    private String trackingNumber;
    @JSONField(name = "ShipperBoxs")
    private List<ShipperBoxsDTO> shipperBoxs;

    @NoArgsConstructor
    @Data
    public static class ShipperBoxsDTO {
        @JSONField(name = "BoxNumber")
        private String boxNumber;
        @JSONField(name = "ShipperHawbcode")
        private String shipperHawbcode;
    }
}
