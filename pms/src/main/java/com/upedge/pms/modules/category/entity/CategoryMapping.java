package com.upedge.pms.modules.category.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class CategoryMapping{

	/**
	 * 
	 */
    private Long aliCnCategoryId;
	/**
	 * 
	 */
    private String aliCnCategoryName;
	/**
	 * 
	 */
    private String aliCateCode;
	/**
	 * 
	 */
    private Long categoryId;
	/**
	 * 
	 */
    private String categoryName;
	/**
	 * 
	 */
    private Date updateTime;

}
