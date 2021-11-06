package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderAttr{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String attrName;
	/**
	 * 
	 */
    private String attrValue;
	/**
	 * 
	 */
    private Date createTime;

}
