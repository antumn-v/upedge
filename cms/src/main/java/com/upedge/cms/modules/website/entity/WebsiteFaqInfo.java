package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqInfo{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String askTitle;
	/**
	 * 
	 */
    private String answerInfo;
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
    private String adminUser;
	/**
	 * 
	 */
    private Long cateId;
	/**
	 * 
	 */
    private Integer state;

    private String cateName;

}
