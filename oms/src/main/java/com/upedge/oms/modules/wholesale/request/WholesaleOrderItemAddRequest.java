package com.upedge.oms.modules.wholesale.request;

import com.upedge.common.utils.PriceUtils;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class WholesaleOrderItemAddRequest{

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
    /**
    * 
    */
    private String adminVariantSku;
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
    private BigDecimal adminVariantPrice;
    /**
    * 
    */
    private BigDecimal adminVariantWeight;
    /**
    * 
    */
    private BigDecimal adminVariantVolume;

    public WholesaleOrderItem toWholesaleOrderItem(){
        WholesaleOrderItem wholesaleOrderItem=new WholesaleOrderItem();
        wholesaleOrderItem.setOrderId(orderId);
        wholesaleOrderItem.setAdminVariantId(adminVariantId);
        wholesaleOrderItem.setAdminProductId(adminProductId);
        wholesaleOrderItem.setAdminVariantSku(adminVariantSku);
        wholesaleOrderItem.setCartId(cartId);
        wholesaleOrderItem.setQuantity(quantity);
        wholesaleOrderItem.setDischargeQuantity(dischargeQuantity);
        wholesaleOrderItem.setUsdPrice(PriceUtils.cnyToUsdByDefaultRate(adminVariantPrice));
        wholesaleOrderItem.setAdminVariantWeight(adminVariantWeight);
        wholesaleOrderItem.setAdminVariantVolume(adminVariantVolume);
        return wholesaleOrderItem;
    }

}
