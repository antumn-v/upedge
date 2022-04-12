package com.upedge.sms.modules.overseaWarehouse.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderFreight{

	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Integer shipType;
	/**
	 * 
	 */
    private BigDecimal shipPrice;

}
