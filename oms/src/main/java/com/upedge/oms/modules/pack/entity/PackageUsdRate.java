package com.upedge.oms.modules.pack.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class PackageUsdRate{

	/**
	 * 
	 */
    private String monthDate;
	/**
	 * 
	 */
    private BigDecimal usdRate;

}
