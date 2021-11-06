package com.upedge.tms.modules.ship.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ShippingAttribute{

	/**
	*
	*/
	private Long id;
	/**
	* 物流属性名称
	*/
	private String attributeName;
	/**
	* 描述
	*/
	private String desc;
	/**
	* 物流属性关联运输模板
	*/
	private Long shippingId;
	/**
	* 赛盒属性id
	*/
	private Integer saiheId;

}
