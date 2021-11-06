package com.upedge.oms.modules.vat.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VatRuleItemRemoveRequest {
    @NotNull
    private Long ruleId;
    @NotNull
    private Long areaId;
    @NotNull
    private String areaName;

}
