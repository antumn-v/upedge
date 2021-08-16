package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
	
	private Long id;

    private Long customerId;

	private Long applicationId;

	private Long accountId;

	private String userName;

    private String loginname;

    private String loginpass;

	//0 超级管理员 1管理员 2普通用户
    private Integer userType;

    private Integer ustatus;

    private OrganizationVo parentOrganization;
	
	private RoleVo role;

	private List<String> permissions = new ArrayList<String>();

	private List<CustomerSettingVo> settingVos = new ArrayList<>();

	private List<Long> orgIds;
	
	public Session() {
		
	}
	
	public Session(UserVo user) {
		this.setId(user.getId());
		this.setCustomerId(user.getCustomerId());
		this.setLoginname(user.getLoginName());
		this.setUserType(user.getUserType());
		this.setUstatus(user.getStatus());
	}
	
	public UserVo toUser() {
		UserVo user = new UserVo();
		user.setId(this.getId());
		user.setCustomerId(this.getCustomerId());
		user.setLoginName(this.getLoginname());
		user.setUserType(this.getUserType());
		user.setStatus(this.getUstatus());
		return user;
	}




}
