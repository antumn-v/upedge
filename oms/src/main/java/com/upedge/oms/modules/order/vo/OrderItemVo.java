package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderItemVo {

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
    private Long storeOrderItemId;
	/**
	 * 
	 */
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Long adminProductId;
	/**
	 * 
	 */
    private String storeVariantName;
	/**
	 * 
	 */
    private String storeVariantSku;
	/**
	 * 
	 */
    private String storeVariantImage;
	/**
	 * 
	 */
    private String storeProductTitle;
	/**
	 * 
	 */
    private BigDecimal usdPrice;

    private BigDecimal cnyPrice;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 抵扣数量
	 */
    private Integer dischargeQuantity;

	private String adminVariantSku;

	private String adminVariantImg;

	private String adminVariantName;

	private Long storeOrderId;

	private String platOrderId;

	private String platOrderName;

	private Long storeId;
    //0=已支付 1=部分退款 2=全部退款
	private Integer financialStatus;
    //0=未发货 1=部分发货 2=全部发货
	private Integer fulfillmentStatus;

	private BigDecimal adminVariantWeight;

	private BigDecimal adminVariantVolume;
    //0=普通产品  1=包装
	private Integer itemType;

	private Date createTime;

	private Date updateTime;

	private String platVariantId;

	private String platProductId;

	private String storeProductId;

	private String platOrderItemId;

}
