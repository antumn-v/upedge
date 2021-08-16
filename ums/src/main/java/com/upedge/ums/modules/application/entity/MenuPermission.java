package com.upedge.ums.modules.application.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class MenuPermission{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long menuId;
	/**
	 * 
	 */
    private String permission;

}
