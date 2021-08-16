package com.upedge.common.model.user.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 当月每一天的日期和当天返点总和
 */
@Data
public class OrderBenefitsVo {

    /**
     * 日期
     */
    String dayDate;


    /**
     * 当天返点总和
     */
    BigDecimal benefitsAmount;
}
