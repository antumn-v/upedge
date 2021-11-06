package com.upedge.oms.modules.reason.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class TrackAgainReason{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String reason;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
