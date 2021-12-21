package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogComment{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long blogId;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private String comment;
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
    private Integer state;
	/**
	 * 
	 */
    private Integer followNum;

	private Integer followState=0;

	private String userName;
}
