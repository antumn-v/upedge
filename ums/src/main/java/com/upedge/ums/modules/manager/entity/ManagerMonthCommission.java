package com.upedge.ums.modules.manager.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ManagerMonthCommission{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long managerId;
	/**
	 * 
	 */
    private Date dateMonth;
	/**
	 * 
	 */
    private BigDecimal totalCommission;

}
