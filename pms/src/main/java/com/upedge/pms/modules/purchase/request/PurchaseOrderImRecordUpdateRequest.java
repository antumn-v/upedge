package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderImRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class PurchaseOrderImRecordUpdateRequest{

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

    public PurchaseOrderImRecord toPurchaseOrderImRecord(Long id){
        PurchaseOrderImRecord purchaseOrderImRecord=new PurchaseOrderImRecord();
        purchaseOrderImRecord.setId(id);
        purchaseOrderImRecord.setPurchaseOrderId(purchaseOrderId);
        purchaseOrderImRecord.setTrackingCode(trackingCode);
        purchaseOrderImRecord.setOperatorId(operatorId);
        purchaseOrderImRecord.setCreateTime(createTime);
        return purchaseOrderImRecord;
    }

}
