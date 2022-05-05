package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
    private Long customerId;
	/**
	 * 
	 */
    private Long userId;
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
