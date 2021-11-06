package com.upedge.common.model.old.ums;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class StaffPermission{

	/**
	 * 
	 */
    private Long staffId;
	/**
	 * 
	 */
    private Integer permissionId;
	/**
	 * 
	 */
    private Integer permissionType;

    private String menuName;


}
