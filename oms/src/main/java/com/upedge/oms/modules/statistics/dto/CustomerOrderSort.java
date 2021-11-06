package com.upedge.oms.modules.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerOrderSort {

    private Long customerId;

    /**
     * 客户名称
     */
    private String userName;

    /**
     * 客户账号
     */
    private String loginName;

    /**
     * 注册日期
     */
    private Date registerDate;

    /**
     * 客户经理
     */
    private String userManager;

    /**
     * 下单数
     */
    private Integer orderPaidNum;

    /**
     * 下单金额
     */
    private BigDecimal orderAmount;

}
