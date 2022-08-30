package com.upedge.pms.modules.product.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VariantNameVo {
    @JSONField(name = "cName")
    private String cName;
    @JSONField(name = "eName")
    private String eName;

    private List<VariantValueVo> values;
}
