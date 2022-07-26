package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class PurchaseOrderTrackingAddRequest{

    /**
    * 
    */
    private Long purchaseOrderId;
    /**
    * 
    */
    private String purchaseId;
    /**
    * 
    */
    private String trackingCode;
    /**
    * 
    */
    private String trackingCompany;
    /**
    * 
    */
    private Date createTime;

    public PurchaseOrderTracking toPurchaseOrderTracking(){
        PurchaseOrderTracking purchaseOrderTracking=new PurchaseOrderTracking();
        purchaseOrderTracking.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderTracking.setPurchaseId(purchaseId);
        purchaseOrderTracking.setTrackingCode(trackingCode);
        purchaseOrderTracking.setTrackingCompany(trackingCompany);
        purchaseOrderTracking.setCreateTime(createTime);
        return purchaseOrderTracking;
    }

}
