package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class PurchaseOrderItemUpdateRequest{

    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private Long varaintId;
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
    /**
     * 
     */
    private BigDecimal price;
    /**
     * 
     */
    private String specId;

    public PurchaseOrderItem toPurchaseOrderItem(Long id){
        PurchaseOrderItem purchaseOrderItem=new PurchaseOrderItem();
        purchaseOrderItem.setId(id);
        purchaseOrderItem.setOrderId(orderId);
        purchaseOrderItem.setVaraintId(varaintId);
        purchaseOrderItem.setProductId(productId);
        purchaseOrderItem.setPurchaseLink(purchaseLink);
        purchaseOrderItem.setVariantName(variantName);
        purchaseOrderItem.setVariantImage(variantImage);
        purchaseOrderItem.setVariantSku(variantSku);
        purchaseOrderItem.setPurchaseSku(purchaseSku);
        purchaseOrderItem.setQuantity(quantity);
        purchaseOrderItem.setPrice(price);
        purchaseOrderItem.setSpecId(specId);
        return purchaseOrderItem;
    }

}
