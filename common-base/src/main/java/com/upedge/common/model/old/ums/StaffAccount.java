package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class StaffAccount{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private String username;
	/**
	 * 
	 */
    private String email;
	/**
	 * 
	 */
    private String salt;
	/**
	 * 
	 */
    private String password;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Integer state;

    private String pwd;

}
