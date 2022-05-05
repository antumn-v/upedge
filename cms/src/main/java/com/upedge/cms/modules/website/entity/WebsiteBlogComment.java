package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
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
    private Long customerId;
	/**
	 * 
	 */
    private Long userId;
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
	 * 0:已禁用 1:已启用 2:已删除
	 */
    private Integer state;
	/**
	 * 
	 */
    private Integer followNum;

	private Integer followState=0;

	private String userName;
}
