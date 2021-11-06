package com.upedge.ums.modules.manager.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ManagerTarget{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String managerCode;
	/**
	 * 目标销售额
	 */
    private BigDecimal targetSalesAmount;
	/**
	 * 目标客户下单数
	 */
    private Integer targetCustomerPlaceOrder;
	/**
	 * 实际销售额
	 */
    private BigDecimal actualSalesAmount;
	/**
	 * 实际下单客户
	 */
    private Integer actualCustomerPlaceOrder;
	/**
	 * 日期月份
	 */
    private String dateMonth;

}
