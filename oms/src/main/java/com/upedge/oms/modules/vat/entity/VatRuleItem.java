package com.upedge.oms.modules.vat.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class VatRuleItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long ruleId;
	/**
	 * 
	 */
    private Long areaId;

    private String areaName;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
