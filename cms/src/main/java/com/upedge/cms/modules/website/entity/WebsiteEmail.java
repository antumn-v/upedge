package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
