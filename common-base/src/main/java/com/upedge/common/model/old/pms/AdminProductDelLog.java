package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminProductDelLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String productSku;
	/**
	 * 
	 */
    private String userId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updaeTime;

}
