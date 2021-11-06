package com.upedge.oms.modules.wholesale.entity;

import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class WholesaleOrderItem{

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

    private Long shippingId;

    private String adminProductTitle;
	/**
	 * 
	 */
    private String adminVariantSku;

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

    private String adminVariantImage;
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

	public WholesaleOrderItem(Cart cart) {
		this.adminProductTitle = cart.getProductTitle();
		this.adminProductId = cart.getProductId();
		this.adminVariantId = cart.getVariantId();
		this.adminVariantImage = cart.getVariantImage();
		this.usdPrice = cart.getPrice();
		this.adminVariantSku = cart.getVariantSku();
		this.adminVariantName = cart.getVariantName();
		this.cartId = cart.getId();
		this.quantity = cart.getQuantity();
		this.dischargeQuantity = 0;
		this.adminVariantWeight = cart.getVariantWeight();
		this.adminVariantVolume = cart.getVariantVolume();
	}

	public WholesaleOrderItem() {
	}
}
