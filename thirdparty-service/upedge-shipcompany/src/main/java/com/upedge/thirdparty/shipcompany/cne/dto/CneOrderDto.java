package com.upedge.thirdparty.shipcompany.cne.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CneOrderDto {


    @JsonProperty("iIndex")
    private Integer iIndex;
    @JsonProperty("iID")
    private String iID;
    @JsonProperty("cNum")
    private String cNum;
    @JsonProperty("cNo")
    private String cNo;
    @JsonProperty("cRNo")
    private String cRNo;
    @JsonProperty("cCNo")
    private String cCNo;
    @JsonProperty("cMess")
    private String cMess;
    @JsonProperty("cPNo")
    private String cPNo;
    @JsonProperty("printUrl")
    private String printUrl;
}
