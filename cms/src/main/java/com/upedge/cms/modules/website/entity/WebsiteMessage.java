package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteMessage{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private String whatsapp;
	/**
	 * 
	 */
    private String facebook;
	/**
	 * 
	 */
    private String skype;
	/**
	 * 
	 */
    private String message;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private String adminUser;
	/**
	 * 
	 */
    private String remark;
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
