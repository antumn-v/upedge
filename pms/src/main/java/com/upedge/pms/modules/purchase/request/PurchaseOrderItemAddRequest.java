package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import lombok.Data;

import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class PurchaseOrderItemAddRequest{

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
    /**
    * 
    */
    private BigDecimal price;
    /**
    * 
    */
    private String specId;

    public PurchaseOrderItem toPurchaseOrderItem(){
        PurchaseOrderItem purchaseOrderItem=new PurchaseOrderItem();
        purchaseOrderItem.setOrderId(orderId);
        purchaseOrderItem.setVariantId(variantId);
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
