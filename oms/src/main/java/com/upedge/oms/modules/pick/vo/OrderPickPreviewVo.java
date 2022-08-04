package com.upedge.oms.modules.pick.vo;

import lombok.Data;

@Data
public class OrderPickPreviewVo {

    Long shipMethodId;

    Integer pickType;

    int skuType;

    int skuQuantity;

    String shipMethodName;

    String shipMethodDesc;

    int total;
}
