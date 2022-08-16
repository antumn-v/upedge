package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class YunExpressShipMethodVo {

    @JsonProperty("Code")
    private String code;
    @JsonProperty("CName")
    private String cName;
    @JsonProperty("EName")
    private String eName;
    @JsonProperty("HasTrackingNumber")
    private Boolean hasTrackingNumber;
    @JsonProperty("DisplayName")
    private String displayName;
}
