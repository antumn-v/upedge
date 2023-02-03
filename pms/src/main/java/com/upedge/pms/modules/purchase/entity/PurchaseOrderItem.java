package com.upedge.pms.modules.purchase.entity;

import com.alibaba.trade.param.AlibabaTradeFastCargo;
import com.upedge.pms.modules.product.entity.ProductVariant;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class PurchaseOrderItem{

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
    private Long variantId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String purchaseLink;
	/**
	 * 
	 */
    private String variantName;
	/**
	 * 
	 */
    private String variantImage;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private String purchaseSku;
	/**
	 * 
	 */
    private Integer quantity;

	private Integer originalQuantity;

	private Integer receiveQuantity;

	private Integer requireQuantity;

	private Integer refundQuantity;
	/**
	 * 
	 */
    private BigDecimal price;

	private BigDecimal originalPrice;
	/**
	 * 
	 */
    private String specId;

	private Long barcode;

	private BigDecimal weight;

	private Integer state;

	private Integer inventory;

	private Integer minOrderQuantity;

	public PurchaseOrderItem(ProductVariant productVariant, AlibabaTradeFastCargo tradeFastCargo) {
		this.variantImage = productVariant.getVariantImage();
		this.variantSku = productVariant.getVariantSku();
		this.variantName = productVariant.getCnName();
		this.variantId = productVariant.getId();
		this.productId = productVariant.getProductId();
		this.purchaseSku = productVariant.getPurchaseSku();
		this.barcode = productVariant.getBarcode();
		this.quantity = tradeFastCargo.getQuantity().intValue();
		this.originalQuantity = tradeFastCargo.getQuantity().intValue();
		this.specId = tradeFastCargo.getSpecId();
		this.purchaseLink = tradeFastCargo.getOfferId().toString();
		this.price = productVariant.getVariantPrice();
		this.originalPrice = productVariant.getVariantPrice();
		this.requireQuantity = tradeFastCargo.getQuantity().intValue();
	}
	public PurchaseOrderItem(ProductVariant productVariant) {
		this.variantImage = productVariant.getVariantImage();
		this.variantSku = productVariant.getVariantSku();
		this.variantName = productVariant.getCnName();
		this.variantId = productVariant.getId();
		this.productId = productVariant.getProductId();
		this.purchaseSku = productVariant.getPurchaseSku();
		this.barcode = productVariant.getBarcode();
		this.price = productVariant.getVariantPrice();
		this.originalPrice = productVariant.getVariantPrice();
	}

	public PurchaseOrderItem() {
	}
}
