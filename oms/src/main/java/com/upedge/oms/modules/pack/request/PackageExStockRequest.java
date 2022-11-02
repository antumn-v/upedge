package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PackageExStockRequest {

    @NotBlank
    private String scanNo;

    @NotBlank
    private String trackCompany;
}
