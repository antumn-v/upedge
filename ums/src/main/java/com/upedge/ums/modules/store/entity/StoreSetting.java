package com.upedge.ums.modules.store.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author author
 */
@Data
public class StoreSetting{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
	@NotNull
    private Long storeId;
	/**
	 * 
	 */
	@NotBlank
    private String settingName;
	/**
	 * 
	 */
	@NotBlank
    private String settingValue;

}
