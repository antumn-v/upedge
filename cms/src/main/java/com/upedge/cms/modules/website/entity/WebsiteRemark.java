package com.upedge.cms.modules.website.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
	 * 0:客户 1:客户经理
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
