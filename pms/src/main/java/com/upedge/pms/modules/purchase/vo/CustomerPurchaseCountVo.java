package com.upedge.pms.modules.purchase.vo;

import lombok.Data;

@Data
public class CustomerPurchaseCountVo {

    private Long customerId;

    private String username;

    private Integer total;
}
