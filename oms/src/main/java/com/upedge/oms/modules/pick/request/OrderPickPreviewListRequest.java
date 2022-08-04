package com.upedge.oms.modules.pick.request;

import lombok.Data;

@Data
public class OrderPickPreviewListRequest {

    private Integer pickType;

    private Long shipMethodId;
}
