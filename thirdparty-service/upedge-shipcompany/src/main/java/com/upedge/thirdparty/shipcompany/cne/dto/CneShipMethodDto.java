package com.upedge.thirdparty.shipcompany.cne.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CneShipMethodDto {


    @JSONField(name = "oName")
    private String oName;
    @JSONField(name = "cName")
    private String cName;
}
