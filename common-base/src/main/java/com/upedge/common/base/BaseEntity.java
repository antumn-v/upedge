package com.upedge.common.base;

import com.upedge.common.mybatis.annotation.Organization;
import com.upedge.common.mybatis.annotation.Tenant;
import com.upedge.common.mybatis.annotation.Time;
import com.upedge.common.mybatis.annotation.User;

import java.util.Date;

public class BaseEntity {

	/**
	 * 租户ID
	 */
	private Long customerId;
	
	/**
	 * 部门ID
	 */
	private Long orgId;
	
	/**
	 * 用户ID
	 */
	private Long userId;
	
	/**
	 * 创建事时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 删除标志 
	 */
	private int flag;

	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * 租户注解，当继承该类的实体类，在数据库操作时会自动注入租户信息。
	 * @param customerId
	 */
	@Tenant
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getOrgId() {
		return orgId;
	}

	@Organization
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	@User
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	@Time
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	@Time
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
