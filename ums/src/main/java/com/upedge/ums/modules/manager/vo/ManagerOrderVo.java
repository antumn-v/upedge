package com.upedge.ums.modules.manager.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerOrderVo {


    private String managerCode;

    Integer normalOrderCount = 0;

    BigDecimal normalOrderAmount = BigDecimal.ZERO;

    Integer placeNormalOrderCustomerCount = 0;

    Integer wholesaleOrderCount = 0;

    BigDecimal wholesaleOrderAmount = BigDecimal.ZERO;

    Integer placeWholesaleOrderCustomerCount = 0;


}
