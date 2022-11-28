package com.upedge.oms.modules.order.entity;

import com.upedge.common.model.old.oms.AppOrderVariant;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyLineItem;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder.ShoplazzaLineItems;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderItem;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StoreOrderItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String platOrderId;
	/**
	 * 
	 */
    private String platOrderItemId;

    private String platVariantId;

    private String platProductId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private BigDecimal price;
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
	 * 0=正常 1=已生成订单
	 */
    private Integer state;

    private Boolean isRemoved;

	public StoreOrderItem(ShopifyLineItem lineItem) {
		this.platOrderItemId = lineItem.getId();
		this.price = lineItem.getPrice();
		this.quantity = lineItem.getQuantity();
		this.storeProductTitle = lineItem.getTitle();
		this.storeVariantSku = lineItem.getSku();
		this.platVariantId = lineItem.getVariant_id();
		this.platProductId = lineItem.getProduct_id();
		if (StringUtils.isNotBlank(lineItem.getVariant_title())){
			this.storeVariantName = lineItem.getVariant_title();
		}else {
			this.storeVariantName = lineItem.getName();
		}
	}


	public StoreOrderItem(ShoplazzaLineItems lineItem) {
		this.platOrderItemId = lineItem.getId();
		this.price = lineItem.getPrice();
		this.quantity = lineItem.getQuantity();
		this.storeProductTitle = lineItem.getProduct_title();
		this.storeVariantName = lineItem.getVariant_title();
		this.storeVariantSku = lineItem.getSku();
		this.platVariantId = lineItem.getVariant_id();
		this.platProductId = lineItem.getProduct_id();
		this.storeVariantImage = lineItem.getImage();
	}

	public StoreOrderItem(WoocommerceOrderItem lineItem) {
		this.platOrderItemId = lineItem.getId();
		this.price = lineItem.getPrice();
		this.quantity = lineItem.getQuantity();
		this.storeProductTitle = lineItem.getParent_name();
		this.storeVariantName = lineItem.getName().substring(lineItem.getName().indexOf(" - ")+3);
		this.storeVariantSku = lineItem.getSku();
		this.platProductId = lineItem.getProduct_id();
		if(lineItem.getVariation_id().equals(0L)){
			this.platVariantId = lineItem.getProduct_id();
		}else{
			this.platVariantId = lineItem.getVariation_id();
		}
	}

	public StoreOrderItem(AppOrderVariant appOrderVariant) {
		this.id = appOrderVariant.getAovId();
		this.storeVariantImage = appOrderVariant.getVariantImg();
		if (null != appOrderVariant.getOriginalItemId() && !appOrderVariant.getOriginalItemId().equals(0L)){
			this.platOrderItemId = appOrderVariant.getOriginalItemId().toString();
		}else {
			this.platOrderItemId = appOrderVariant.getAovId().toString();
		}
		if (appOrderVariant.getOriginalVariantId() != null){
			this.platVariantId = appOrderVariant.getOriginalVariantId().toString();
		}else {
			if (null != appOrderVariant.getVariantId()) {
				this.platVariantId = appOrderVariant.getVariantId().toString();
			}
		}
		if (appOrderVariant.getOriginalProductId() != null){
			this.platProductId = appOrderVariant.getOriginalProductId().toString();
		}else {
			if (null != appOrderVariant.getProductId()) {
				this.platProductId = appOrderVariant.getProductId().toString();
			}
		}
		this.storeOrderId = appOrderVariant.getOrderId();
		this.storeVariantId = appOrderVariant.getVariantId();
		this.storeProductId = appOrderVariant.getProductId();
		this.storeVariantSku = appOrderVariant.getSku();
		this.storeVariantName = appOrderVariant.getVariantTitle();
		this.storeProductTitle = appOrderVariant.getTitle();
		this.quantity = appOrderVariant.getQuantity();
		this.price = appOrderVariant.getPrice();
		this.state = 0;
		this.isRemoved = false;
	}

	public StoreOrderItem() {
	}
}
