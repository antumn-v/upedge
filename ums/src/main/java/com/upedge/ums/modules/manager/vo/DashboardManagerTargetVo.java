package com.upedge.ums.modules.manager.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardManagerTargetVo {

    private static final long serialVersionUID = 1L;

    /**
     * 客户经理
     */
    private String userManager;
    /**
     * 日期月份
     */
    private String dateMonth;
    /**
     * 目标销售额
     */
    private BigDecimal targetSaleAmount=new BigDecimal(10000);
    /**
     * 目标客户下单数
     */
    private Integer targetOrderUserNum=10;
    /**
     * 真实销售额
     */
    private BigDecimal realSaleAmount= BigDecimal.ZERO;
    /**
     * 真实下单客户数
     */
    private Integer realOrderUserNum=0;
    private Integer start;
    private Integer pageSize;

}
