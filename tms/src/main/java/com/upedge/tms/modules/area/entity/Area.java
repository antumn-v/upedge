package com.upedge.tms.modules.area.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class Area{

	/**
	 * 
	 */
private Long id;
	/**
	 * 地区名称
	 */
private String name;
	/**
	 * 英文名
	 */
private String enName;
	/**
	 * 地区代码
	 */
private String areaCode;
	/**
	 * 
	 */
private Date updateTime;

}
