package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class WebsiteBlogImg{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String img;
	/**
	 * 
	 */
    private Long blogId;
	/**
	 * 
	 */
    private Integer state;

}
