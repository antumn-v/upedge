package com.upedge.oms.modules.vat.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class VatRuleAssignCustomerRequest {

    @NotNull
    private Long ruleId;

    @Size(min = 1)
    private List<Long> customerIds;
}
