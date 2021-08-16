package com.upedge.ums.modules.user.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Customer{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String cname;
	/**
	 * 0 正常 1 未激活 2 锁定 3注销
	 */
    private Integer cstatus;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 客户注册后创建的默认用户ID
	 */
    private Long customerSignupUserId;

}
