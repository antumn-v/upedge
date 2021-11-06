package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class FbUser{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private String fbUserId;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
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
