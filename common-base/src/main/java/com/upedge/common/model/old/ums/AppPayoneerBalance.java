package com.upedge.common.model.old.ums;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AppPayoneerBalance{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String balanceId;
	/**
	 * 
	 */
    private String accountId;
	/**
	 * 
	 */
    private String statusName;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 
	 */
    private String type;
	/**
	 * 
	 */
    private String availableBalance;
	/**
	 * 
	 */
    private String status;
	/**
	 * 
	 */
    private String updateTime;

}
