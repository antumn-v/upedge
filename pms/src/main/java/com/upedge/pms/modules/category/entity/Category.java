package com.upedge.pms.modules.category.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Category{

	/**
	 * 
	 */
    private Long id;
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
    private BigDecimal treeSort;

}
