package com.upedge.common.model.old.ums;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class MqMessageRecord{

	/**
	 * 
	 */
    private String id;
	/**
	 * 
	 */
    private String body;
	/**
	 * 
	 */
    private String topic;
	/**
	 * 
	 */
    private Date sendTime;
	/**
	 * 
	 */
    private Date customerTime;
	/**
	 * 
	 */
    private Integer sendSuccess;

}
