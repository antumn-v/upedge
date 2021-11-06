package com.upedge.common.model.order.vo;

import lombok.Data;

import java.util.Set;

/**
 * 获取 本月付款批发订单金额 vo
 */
@Data
public class AllOrderAmountVo {

   private Set<String> managerCodeSet;

   private String startDay;

   private String endDay;

   private String managerCode;
}
