package com.upedge.ums.modules.user.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class CustomerSetting{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private String settingName;
	/**
	 * 
	 */
    private String settingValue;

}
