package com.upedge.oms.modules.rules.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class ShipRuleConditionDto {

    private Long customerId;

    private BigDecimal amount;

    private BigDecimal freight;

    private String country;

    private Long areaId;

    private Long shipTemplateId;
}
