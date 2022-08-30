package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class YunExpressShipMethodVo {

    @JSONField(name = "Code")
    private String code;
    @JSONField(name = "CName")
    private String cName;
    @JSONField(name = "EName")
    private String eName;
    @JSONField(name = "HasTrackingNumber")
    private Boolean hasTrackingNumber;
    @JSONField(name = "DisplayName")
    private String displayName;
}
