package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class VatRule{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 最低价格
	 */
    private BigDecimal minAmount;
	/**
	 * 最高价格
	 */
    private BigDecimal maxAmount;
	/**
	 * 申请方式   1订单金额（订单产品金额+运费金额）      2订单产品金额
	 */
    private Integer methodType;
	/**
	 * 申报比例
	 */
    private BigDecimal ratio;
	/**
	 * 货币
	 */
    private String currency;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String adminUser;

}
