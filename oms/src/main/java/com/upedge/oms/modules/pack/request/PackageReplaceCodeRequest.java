package com.upedge.oms.modules.pack.request;

import lombok.Data;

@Data
public class PackageReplaceCodeRequest {

    private Long packNo;

    private String trackingCode;
    /**
     * 物流商单号
     */
    private String logisticsOrderNo;
}
