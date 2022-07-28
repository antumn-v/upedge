package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderImItem;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PurchaseOrderImItemUpdateRequest{

    /**
     * 
     */
    private Long orderImId;
    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private Integer quantity;

    public PurchaseOrderImItem toPurchaseOrderImItem(Long id){
        PurchaseOrderImItem purchaseOrderImItem=new PurchaseOrderImItem();
        purchaseOrderImItem.setId(id);
        purchaseOrderImItem.setOrderImId(orderImId);
        purchaseOrderImItem.setVariantId(variantId);
        purchaseOrderImItem.setQuantity(quantity);
        return purchaseOrderImItem;
    }

}
