package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gx
 */
@Data
public class WebsiteBlogInfo{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String title;
	/**
	 * 
	 */
    private String urlSuf;
	/**
	 * 
	 */
    private String seoImg;
	/**
	 * 
	 */
    private String img;
	/**
	 * 
	 */
    private String shortInfo;
	/**
	 * 
	 */
    private String content;
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
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Integer viewNum;
	/**
	 * 
	 */
    private Integer followNum;
	/**
	 * 
	 */
    private String keywords;
	/**
	 * 
	 */
    private String description;
	/**
	 * 
	 */
    private Integer state;

    //============================

	private Long commentNum;

	private List<WebsiteBlogComment> commentList=new ArrayList<>();

	private Integer followState=0;
}
