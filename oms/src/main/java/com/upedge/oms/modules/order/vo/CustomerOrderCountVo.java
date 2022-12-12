package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class CustomerOrderCountVo {

    private Long customerId;

    private String username;

    private Long actualShipMethodId;

    private String shipMethodName;

    private Integer pickType;

    private Long total;
}
