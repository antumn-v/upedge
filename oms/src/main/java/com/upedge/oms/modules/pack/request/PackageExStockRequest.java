package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class PackageExStockRequest {

    @NotBlank
    private String scanNo;

    @NotBlank
    private String trackCompany;

    private List<Long> orderIds;
}
