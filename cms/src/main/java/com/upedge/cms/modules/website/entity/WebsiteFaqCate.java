package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
