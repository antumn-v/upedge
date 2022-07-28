package com.upedge.pms.modules.purchase.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImItem;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PurchaseOrderImItemAddRequest{

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

    public PurchaseOrderImItem toPurchaseOrderImItem(){
        PurchaseOrderImItem purchaseOrderImItem=new PurchaseOrderImItem();
        purchaseOrderImItem.setOrderImId(orderImId);
        purchaseOrderImItem.setVariantId(variantId);
        purchaseOrderImItem.setQuantity(quantity);
        return purchaseOrderImItem;
    }

}
