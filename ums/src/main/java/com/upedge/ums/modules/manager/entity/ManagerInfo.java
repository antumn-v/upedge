package com.upedge.ums.modules.manager.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
    private Long userId;
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
	 * 0=客户经理，1=助理
	 */
    private Integer managerType;
	/**
	 * 1=正常 0=停用,2=删除
	 */
    private Integer managerState;
	/**
	 * 客户经理英文名
	 */
    private String managerName;
	/**
	 * 助理所属的客户经理代码
	 */
    private String assistantSupeior;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 创建者
	 */
    private Long creatorId;
	/**
	 * 邀请注册码
	 */
    private String inviteCode;

}
