package com.upedge.sms.modules.wholesale.request;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class WholesaleOrderItemUpdateRequest{

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
    private String productTitle;
    /**
     * 
     */
    private String variantSku;
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
    private String variantName;
    /**
     * 
     */
    private String variantImage;
    /**
     * 
     */
    private BigDecimal price;
    /**
     * 
     */
    private BigDecimal variantWeight;
    /**
     * 
     */
    private BigDecimal variantVolume;

    public WholesaleOrderItem toWholesaleOrderItem(Long id){
        WholesaleOrderItem wholesaleOrderItem=new WholesaleOrderItem();
        wholesaleOrderItem.setId(id);
        wholesaleOrderItem.setOrderId(orderId);
        wholesaleOrderItem.setVariantId(variantId);
        wholesaleOrderItem.setProductId(productId);
        wholesaleOrderItem.setProductTitle(productTitle);
        wholesaleOrderItem.setVariantSku(variantSku);
        wholesaleOrderItem.setCartId(cartId);
        wholesaleOrderItem.setQuantity(quantity);
        wholesaleOrderItem.setDischargeQuantity(dischargeQuantity);
        wholesaleOrderItem.setVariantName(variantName);
        wholesaleOrderItem.setVariantImage(variantImage);
        wholesaleOrderItem.setPrice(price);
        wholesaleOrderItem.setVariantWeight(variantWeight);
        wholesaleOrderItem.setVariantVolume(variantVolume);
        return wholesaleOrderItem;
    }

}
