package com.upedge.oms.modules.pack.request;

import lombok.Data;

@Data
public class OrderPackageInfoGetRequest {

    private Long orderId;

    private Long packNo;

    private String trackingCode;
}
