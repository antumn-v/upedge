package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class PurchaseOrderUpdateRequest{

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

    public PurchaseOrder toPurchaseOrder(Long id){
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setId(id);
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
