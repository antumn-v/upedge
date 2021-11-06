package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class WholesaleOrderItemVo {

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Long adminProductId;

    private String adminProductTitle;
	/**
	 * 
	 */
    private String adminVariantSku;

	private String adminVariantImg;

	private String adminVariantName;

	/**
	 * 
	 */
    private Long cartId;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Integer dischargeQuantity;
	/**
	 * 
	 */
    private BigDecimal usdPrice;
	/**
	 * 
	 */
    private BigDecimal adminVariantWeight;
	/**
	 * 
	 */
    private BigDecimal adminVariantVolume;

    //赛盒状态 0:未导入赛盒 1:已导入赛盒
	private Integer saiheState;
}
