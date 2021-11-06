package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
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
