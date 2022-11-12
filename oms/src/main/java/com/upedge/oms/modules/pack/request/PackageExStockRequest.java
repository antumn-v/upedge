package com.upedge.oms.modules.pack.request;

import lombok.Data;

import java.util.List;

@Data
public class PackageExStockRequest {

    private String scanNo;

    private String trackCompany;

    private List<Long> packNos;
}
