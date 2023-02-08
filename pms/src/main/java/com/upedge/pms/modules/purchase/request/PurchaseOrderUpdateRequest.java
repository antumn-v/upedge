package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PurchaseOrderUpdateRequest{

    /**
     * 
     */
    private String remark;

    public PurchaseOrder toPurchaseOrder(Long id){
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setId(id);
        purchaseOrder.setRemark(remark);
        return purchaseOrder;
    }

}
