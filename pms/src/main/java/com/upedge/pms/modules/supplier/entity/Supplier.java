package com.upedge.pms.modules.supplier.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Supplier{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String loginId;
	/**
	 * 
	 */
    private String supplierName;
	/**
	 * 
	 */
    private String supplierLink;
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
    private String companyName;
	/**
	 * 
	 */
    private String categoryName;

}
