package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class WholesaleRefundItemUpdateRequest{

    /**
     * 
     */
    private Long orderId;
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
    private Long refundId;
    /**
     * 
     */
    private Long orderItemId;
    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private String variantImage;

    public WholesaleRefundItem toWholesaleRefundItem(Integer id){
        WholesaleRefundItem wholesaleRefundItem=new WholesaleRefundItem();
        wholesaleRefundItem.setId(id);
        wholesaleRefundItem.setOrderId(orderId);
        wholesaleRefundItem.setQuantity(quantity);
        wholesaleRefundItem.setPrice(price);
        wholesaleRefundItem.setRefundId(refundId);
        wholesaleRefundItem.setOrderItemId(orderItemId);
        wholesaleRefundItem.setVariantSku(variantSku);
        wholesaleRefundItem.setVariantImage(variantImage);
        return wholesaleRefundItem;
    }

}
