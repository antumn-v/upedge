package com.upedge.pms.modules.purchase.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class PurchaseOrderAddRequest{

    /**
    * 
    */
    private String purchaseId;
    /**
    * 
    */
    private BigDecimal productAmount;
    /**
    * 
    */
    private BigDecimal shipPrice;
    /**
    * 
    */
    private BigDecimal amount;
    /**
    * 
    */
    private BigDecimal discountAmount;
    /**
    * 
    */
    private String supplierName;
    /**
    * 
    */
    private Integer purchaseState;
    /**
    * 0=1688采购
    */
    private Integer purchaseType;
    /**
    * 
    */
    private Long operatorId;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;

    public PurchaseOrder toPurchaseOrder(){
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setPurchaseId(purchaseId);
        purchaseOrder.setProductAmount(productAmount);
        purchaseOrder.setShipPrice(shipPrice);
        purchaseOrder.setAmount(amount);
        purchaseOrder.setDiscountAmount(discountAmount);
        purchaseOrder.setSupplierName(supplierName);
        purchaseOrder.setPurchaseState(purchaseState);
        purchaseOrder.setPurchaseType(purchaseType);
        purchaseOrder.setOperatorId(operatorId);
        purchaseOrder.setCreateTime(createTime);
        purchaseOrder.setUpdateTime(updateTime);
        return purchaseOrder;
    }

}
