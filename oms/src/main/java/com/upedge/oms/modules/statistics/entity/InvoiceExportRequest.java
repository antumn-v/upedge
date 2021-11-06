package com.upedge.oms.modules.statistics.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Ï¦Îí
 */
@Data
public class InvoiceExportRequest{

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
    private String rangeBegin;
	/**
	 * 
	 */
    private String rangeEnd;
	/**
	 * 
	 */
    private String excelName;
	/**
	 * 
	 */
    private String excelUrl;
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
