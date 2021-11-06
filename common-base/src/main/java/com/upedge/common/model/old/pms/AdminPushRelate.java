package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminPushRelate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private Long sibProductId;
	/**
	 * 0:等待,1:接受,2:拒绝
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
