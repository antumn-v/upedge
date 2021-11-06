package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminPriceApply{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private String applyUserId;
	/**
	 * 
	 */
    private String executeUserId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 原因
	 */
    private String reason;

}
