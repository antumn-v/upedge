package com.upedge.oms.modules.cart.entity;

import com.upedge.common.model.product.VariantDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class Cart{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 产品ID
	 */
    private Long productId;
	/**
	 * 
	 */
    private String productTitle;
	/**
	 * 变体ID
	 */
    private Long variantId;
	/**
	 * 
	 */
    private String variantName;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private String variantImage;

    private BigDecimal variantWeight;

    private BigDecimal variantVolume;
	/**
	 * 数量
	 */
    private Integer quantity;
	/**
	 * 单价
	 */
    private BigDecimal price;
	/**
	 * 正常0，被删除2，创建订单1
	 */
    private Integer state = 0;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 备库=0，批发=1
	 */
    private Integer cartType;

	public static Cart variantToCart(VariantDetail detail, Date date){
		Cart cart = new Cart();
		cart.setProductId(detail.getProductId());
		cart.setProductTitle(detail.getProductTitle());
		cart.setState(0);
		cart.setVariantId(detail.getVariantId());
		cart.setVariantName(detail.getVariantName());
		cart.setVariantSku(detail.getVariantSku());
		cart.setVariantImage(detail.getVariantImage());
		cart.setPrice(detail.getUsdPrice());
		cart.setVariantWeight(detail.getWeight());
		cart.setVariantVolume(detail.getVolume());
		cart.setCreateTime(date);
		cart.setUpdateTime(date);
		return cart;
	}

	public Cart(VariantDetail detail, Date date) {
		this.productId = detail.getProductId();
		this.productTitle = detail.getProductTitle();
		this.state = 0;
		this.variantId = detail.getVariantId();
		this.variantName = detail.getVariantName();
		this.variantSku = detail.getVariantSku();
		this.variantImage = detail.getVariantImage();
		this.price = detail.getUsdPrice();
		this.variantWeight = detail.getWeight();
		this.variantVolume = detail.getVolume();
		this.createTime = date;
		this.updateTime = date;
	}

	public Cart() {
	}
}
