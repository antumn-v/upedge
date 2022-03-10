package com.upedge.oms.modules.order.entity;

import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.utils.PriceUtils;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderItem{

	public static Integer QUOTE_STATE_UNQU0TED = 0;
	public static Integer QUOTE_STATE_QUOTED = 1;
	public static Integer QUOTE_STATE_NOT_FIND = 2;
	public static Integer QUOTE_STATE_CANCELED = 3;
	public static Integer QUOTE_STATE_NO_STOCK = 4;
	public static Integer QUOTE_STATE_QUOTING= 5;
	public static Integer QUOTE_STATE_UPEDGE= 6;

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
	private Long storeOrderId;

    private Long storeOrderItemId;
	/**
	 * 
	 */
    private Long storeVariantId;

    private Long storeProductId;
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

	private String storeVariantImage;
	/**
	 * 
	 */
    private String adminVariantSku;
    private String adminVariantImage;
	/**
	 * 
	 */
    private String storeProductTitle;
	/**
	 * 
	 */
    private BigDecimal usdPrice;

    private BigDecimal cnyPrice;

    private BigDecimal usdRate;

    private BigDecimal adminVariantWeight;

    private BigDecimal adminVariantVolume;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 抵扣数量
	 */
    private Integer dischargeQuantity;

    private Integer itemType;

    private Long shippingId;

	private Integer quoteState;

	private BigDecimal width;

	private BigDecimal height;

	private BigDecimal length;


	public OrderItem(StoreOrderItem storeOrderItem) {
		this.storeVariantId = storeOrderItem.getStoreVariantId();
		this.storeProductTitle = storeOrderItem.getStoreProductTitle();
		this.quantity = storeOrderItem.getQuantity();
		this.storeVariantSku = storeOrderItem.getStoreVariantSku();
		this.storeVariantName = storeOrderItem.getStoreVariantName();
		this.storeVariantImage = storeOrderItem.getStoreVariantImage();
		this.storeOrderItemId = storeOrderItem.getId();
	}

	public OrderItem(RelateVariantVo variantVo){
		this.adminVariantId = variantVo.getVariantId();
		this.adminProductId = variantVo.getProductId();
		this.cnyPrice = variantVo.getCnyPrice();
		this.usdPrice = variantVo.getUsdPrice();
		this.adminVariantVolume = variantVo.getVolume();
		this.adminVariantWeight = variantVo.getWeight();
		this.adminVariantImage = variantVo.getImage();
		this.adminVariantSku = variantVo.getSku();
		this.usdRate = new BigDecimal("6.3");
	}

	public OrderItem(CustomerProductQuoteVo variantVo){
		this.adminVariantId = variantVo.getVariantId();
		this.adminProductId = variantVo.getProductId();
		this.shippingId = variantVo.getProductShippingId();
		this.cnyPrice = variantVo.getCnyPrice();
		this.usdPrice = PriceUtils.cnyToUsdByDefaultRate(variantVo.getQuotePrice());
		this.adminVariantVolume = variantVo.getVolume();
		this.adminVariantWeight = variantVo.getWeight();
		this.adminVariantImage = variantVo.getVariantImage();
		this.adminVariantSku = variantVo.getVariantSku();
		this.usdRate = new BigDecimal("6.3");
		this.width = variantVo.getWidth();
		this.length = variantVo.getLength();
		this.height = variantVo.getHeight();
	}

	public void initItemQuoteDetail(CustomerProductQuoteVo variantVo){
		this.adminVariantId = variantVo.getVariantId();
		this.adminProductId = variantVo.getProductId();
		this.shippingId = variantVo.getProductShippingId();
		this.cnyPrice = variantVo.getCnyPrice();
		this.usdPrice = PriceUtils.cnyToUsdByDefaultRate(variantVo.getQuotePrice());
		this.adminVariantVolume = variantVo.getVolume();
		this.adminVariantWeight = variantVo.getWeight();
		this.adminVariantImage = variantVo.getVariantImage();
		this.adminVariantSku = variantVo.getVariantSku();
		this.usdRate = new BigDecimal("6.3");
		this.width = variantVo.getWidth();
		this.length = variantVo.getLength();
		this.height = variantVo.getHeight();
	}


	public OrderItem() {
	}
}
