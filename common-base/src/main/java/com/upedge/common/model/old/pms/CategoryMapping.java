package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
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
