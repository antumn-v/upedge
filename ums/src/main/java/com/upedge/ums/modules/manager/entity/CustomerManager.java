package com.upedge.ums.modules.manager.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class CustomerManager{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 客户注册后创建的默认用户ID
	 */
    private Long customerSignupUserId;
	/**
	 * 用户经理ID
	 */
    private String managerCode;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * admin用户信息完善状态，对应app_user_info 0:未完善 1:部分完善 2:已完善
	 */
    private Integer customerInfoState;

    private Boolean isAuthStore;

}
