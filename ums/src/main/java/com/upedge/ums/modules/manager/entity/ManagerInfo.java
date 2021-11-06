package com.upedge.ums.modules.manager.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class ManagerInfo{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 赛盒来源渠道id
	 */
    private Integer orderSourceId;
	/**
	 * 赛盒来源渠道名称
	 */
    private String orderSourceName;
	/**
	 * 
	 */
    private String managerCode;
	/**
	 * 客户经理别名
	 */
    private String managerName;
	/**
	 * 0=客户经理，1=助理
	 */
    private Integer managerType;
	/**
	 * 1=正常 0=停用
	 */
    private Integer managerState;
	/**
	 * 助理所属的客户经理ID
	 */
    private String assistantSupeior;

    private Date createTime;
	/**
	 * 创建者客户ID
	 */
	private Long creatorUserId;

}
