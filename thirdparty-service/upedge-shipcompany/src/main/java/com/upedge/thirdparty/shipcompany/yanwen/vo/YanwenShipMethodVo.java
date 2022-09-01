package com.upedge.thirdparty.shipcompany.yanwen.vo;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class YanwenShipMethodVo {


    @JSONField(name = "Status")
    private String status;
    @JSONField(name = "LimitStatus")
    private String limitStatus;
    @JSONField(name = "NameEn")
    private String nameEn;
    @JSONField(name = "Id")
    private String id;
    @JSONField(name = "Name")
    private String name;
}
