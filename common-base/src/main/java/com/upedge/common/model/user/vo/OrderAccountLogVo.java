package com.upedge.common.model.user.vo;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 根据订单id + 订单类型查询该订单支付信息
 */
@Data
public class OrderAccountLogVo {

    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal rebate = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

}
