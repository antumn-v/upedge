package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteEmail{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 操作IP地址
	 */
    private String remoteAddr;
	/**
	 * 设备名称/操作系统
	 */
    private String deviceName;
	/**
	 * 
	 */
    private Integer state;

}
