package com.upedge.ums.modules.organization.entity;

import java.io.Serializable;
import java.util.Date;

import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.user.entity.Role;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Organization{

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
    private String orgPath;
	/**
	 * 
	 */
    private String orgName;
	/**
	 * 
	 */
    private Long orgParent;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

	public Role createDefaultRole(Long applicationId,Long customerId){

		Role role=new Role();
		role.setId(IdGenerate.nextId());
		role.setRoleName("default");
		role.setRoleCode(String.valueOf(System.currentTimeMillis()));
		role.setUpdateTime(new Date());
		role.setCreateTime(new Date());
		role.setApplicationId(applicationId);
		role.setCustomerId(customerId);
		return role;

	}

}
