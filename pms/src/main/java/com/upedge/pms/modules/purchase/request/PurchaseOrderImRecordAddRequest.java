package com.upedge.pms.modules.purchase.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class PurchaseOrderImRecordAddRequest{

    /**
    * 
    */
    private Long purchaseOrderId;
    /**
    * 
    */
    private String trackingCode;
    /**
    * 
    */
    private Long operatorId;
    /**
    * 
    */
    private Date createTime;

    public PurchaseOrderImRecord toPurchaseOrderImRecord(){
        PurchaseOrderImRecord purchaseOrderImRecord=new PurchaseOrderImRecord();
        purchaseOrderImRecord.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderImRecord.setTrackingCode(trackingCode);
        purchaseOrderImRecord.setOperatorId(operatorId);
        purchaseOrderImRecord.setCreateTime(createTime);
        return purchaseOrderImRecord;
    }

}
