package com.upedge.thirdparty.shipcompany.cne.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CneOrderDto {


    @JSONField(name = "iIndex")
    private Integer iIndex;
    @JSONField(name = "iID")
    private String iID;
    @JSONField(name = "cNum")
    private String cNum;
    @JSONField(name = "cNo")
    private String cNo;
    @JSONField(name = "cRNo")
    private String cRNo;
    @JSONField(name = "cCNo")
    private String cCNo;
    @JSONField(name = "cMess")
    private String cMess;
    @JSONField(name = "cPNo")
    private String cPNo;
    @JSONField(name = "printUrl")
    private String printUrl;
}
