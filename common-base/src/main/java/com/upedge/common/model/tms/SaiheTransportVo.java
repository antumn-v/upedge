package com.upedge.common.model.tms;

import lombok.Data;

/**
 * @author author
 */
@Data
public class SaiheTransportVo {

	/**
	*
	*/
	private Integer id;
	/**
	*
	*/
	private String carrierName;
	/**
	*
	*/
	private String transportName;
	/**
	*
	*/
	private String transportNameEn;
	/**
	*
	*/
	private Boolean isRegistered;
	/**
	*
	*/
	private String trackingMoreCode;
	/**
	* 17track运输商编码
	*/
	private String fcCode;

}
