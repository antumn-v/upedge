package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminPriceItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 
	 */
    private Long applyId;
	/**
	 * 
	 */
    private BigDecimal currPrice;
	/**
	 * 
	 */
    private BigDecimal price;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Date createTime;

}
