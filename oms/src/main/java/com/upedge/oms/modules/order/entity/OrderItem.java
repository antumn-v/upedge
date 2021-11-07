package com.upedge.oms.modules.order.entity;

import com.upedge.common.model.old.oms.AppOrderVariant;
import com.upedge.common.model.product.RelateVariantVo;
import com.upedge.common.utils.IdGenerate;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderItem{

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


	public OrderItem(StoreOrderItem storeOrderItem) {
		this.storeVariantId = storeOrderItem.getStoreVariantId();
		this.storeProductTitle = storeOrderItem.getStoreProductTitle();
		this.quantity = storeOrderItem.getQuantity();
		this.storeVariantSku = storeOrderItem.getStoreVariantSku();
		this.storeVariantName = storeOrderItem.getStoreVariantName();
		this.adminVariantImage = storeOrderItem.getStoreVariantImage();
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



	public OrderItem() {
	}
}