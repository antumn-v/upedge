package com.upedge.ums.modules.user.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Role{

	public static final Integer SYSTEM_DEFAULT_ROLE = 0;

	public static final Integer ORGANIZATION_ROLE = 1;

	public static final Integer CUSTOMER_ROLE = 2;


	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String roleCode;
	/**
	 * 
	 */
    private String roleName;
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
    private Long applicationId;
	/**
	 * 0=应用默认角色，1=部门自定义角色，2=客户自定义角色
	 */
    private Integer roleType;

}
