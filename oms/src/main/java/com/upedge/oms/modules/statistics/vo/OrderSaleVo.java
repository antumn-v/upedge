package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSaleVo {

    /**
     * 订单id
     */
    private String id;

    /**
     * 销售额
     */
    private BigDecimal amount;

}
