package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
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
	/**
	 * 
	 */
    private String remark;
	/**
	 * 
	 */
    private Long shippingAttributeId;
	/**
	 * 
	 */
    private String shippingTemp;
	/**
	 * 
	 */
    private String adminCategoryTemp;
	/**
	 * 
	 */
    private Long adminCategoryId;

}
