package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
