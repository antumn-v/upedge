package com.upedge.tms.modules.ship.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ShippingTemplate{

	/**
	 * 
	 */
private Long id;
	/**
	 * 模板名称
	 */
private String name;
	/**
	 * 描述
	 */
private String desc;
	/**
	 * 1:启用 0:禁用
	 */
private Integer state;
	/**
	 * 创建时间
	 */
private Date createTime;
	/**
	 * 更新时间
	 */
private Date updateTime;

private Integer saiheId;

}
