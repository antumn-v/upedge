package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
    private Long customerId;
	/**
	 * 
	 */
    private Long userId;
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
