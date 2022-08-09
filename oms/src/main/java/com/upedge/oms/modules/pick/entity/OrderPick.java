package com.upedge.oms.modules.pick.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class OrderPick{

	public static Integer CANCELED = -1;

	public static Integer TO_BE_PICKED = 0;

	public static Integer TWICE_PICK = 1;

	public static Integer TO_BE_PACKED = 2;

	public static Integer PACKING = 3;

	public static Integer TO_BE_SHIPPED = 4;

	public static Integer SHIPPED = 5;

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Integer pickType;
	/**
	 * 
	 */
    private Long shipMethodId;
	/**
	 * -1=作废，0=待拣货，1=待包装，2=包装中，3=已发货
	 */
    private Integer pickState;
	/**
	 * 
	 */
    private Integer skuQuantity;
	/**
	 * 
	 */
    private Integer skuType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long operatorId;

}
