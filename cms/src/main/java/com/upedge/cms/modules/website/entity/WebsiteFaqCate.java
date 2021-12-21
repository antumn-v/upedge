package com.upedge.cms.modules.website.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class WebsiteFaqCate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private Integer sort;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Integer state;

}
