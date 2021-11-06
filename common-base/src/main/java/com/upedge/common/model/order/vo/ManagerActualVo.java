package com.upedge.common.model.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerActualVo {

    /**
     * 客户经理id
     */
    private Long managerCode;

    /**
     * 实际金额
     */
    private BigDecimal actualSalesAmount = BigDecimal.ZERO;

    /**
     * 实际客户数
     */
    private Integer actualNum = 0;
}
