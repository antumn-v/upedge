package com.upedge.oms.modules.pack.dto;

import lombok.Data;

@Data
public class PackageInfoImportDto {

    private String platId;

    private String storeName;

    private String platOrderName;

    private String trackingCode;

    private String logisticsOrderNo;

    private String trackingCompany;

    private String shipMethod;
}
