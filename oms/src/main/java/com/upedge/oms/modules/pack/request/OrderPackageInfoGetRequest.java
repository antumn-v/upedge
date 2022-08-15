package com.upedge.oms.modules.pack.request;

import lombok.Data;

@Data
public class OrderPackageInfoGetRequest {

    private Integer packageNo;

    private String trackingCode;
}
