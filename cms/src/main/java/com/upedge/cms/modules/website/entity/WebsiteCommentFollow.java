package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteCommentFollow{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long commentId;
	/**
	 * 
	 */
    private String appUserId;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date updateTime;

	/**
	 *
	 */
	private String userName;
}
