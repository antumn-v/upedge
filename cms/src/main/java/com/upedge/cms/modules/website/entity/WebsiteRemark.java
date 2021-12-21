package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteRemark{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Integer sort;
	/**
	 * 
	 */
    private Integer type;
	/**
	 * 
	 */
    private String msg;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private String image;
	/**
	 * 
	 */
    private Integer index;
	/**
	 * 
	 */
    private Integer state;

}
