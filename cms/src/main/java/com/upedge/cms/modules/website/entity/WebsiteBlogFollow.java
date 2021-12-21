package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteBlogFollow{

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
    private String appUserId;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date updateTime;

    private String userName;
}
