package com.upedge.pms.modules.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VariantNameVo {
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("eName")
    private String eName;

    private List<VariantValueVo> values;
}
