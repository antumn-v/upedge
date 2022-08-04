package com.upedge.oms.modules.pick.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class OrderPick{

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
