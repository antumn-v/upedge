package com.upedge.ums.modules.organization.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class OrganizationRole{

	/**
	 * 
	 */
    private Long orgId;
	/**
	 * 
	 */
    private Long roleId;
	/**
	 * 0:自己 1:本部门 2:本部门及子部门
	 */
    private Integer dataType;

}
