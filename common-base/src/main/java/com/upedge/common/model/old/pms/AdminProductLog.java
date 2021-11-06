package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminProductLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 操作类型 1:修改实重 2:修改体积重 3:修改运输模板
	 */
    private Integer optType;
	/**
	 * 
	 */
    private String oldInfo;
	/**
	 * 
	 */
    private String newInfo;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String adminUser;

}
