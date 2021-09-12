package com.upedge.pms.modules.category.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class AliCnCategory{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String cateCode;
	/**
	 * 
	 */
    private String cateName;
	/**
	 * 
	 */
    private String parentCode;
	/**
	 * 
	 */
    private String parentName;
	/**
	 * 
	 */
    private String pathName;
	/**
	 * 
	 */
    private String pathCode;
	/**
	 * 
	 */
    private Date createTime;

}
