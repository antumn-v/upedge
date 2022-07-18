package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class PurchasePlanUpdateRequest{

    /**
     * 
     */
    private String purchaseSku;
    /**
     * 
     */
    private String supplierName;
    /**
     * 
     */
    private Long productId;
    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private String cnName;
    /**
     * 
     */
    private String variantImage;
    /**
     * 
     */
    private String purchaseLink;
    /**
     * 
     */
    private Integer quantity;
    /**
     * 
     */
    private String specId;
    /**
     * 
     */
    private Long operatorId;
    /**
     * 0=计划中 1=已生成 -1=已取消
     */
    private Integer state;
    /**
     * 
     */
    private Long purchaseOrderId;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public PurchasePlan toPurchasePlan(Integer id){
        PurchasePlan purchasePlan=new PurchasePlan();
        purchasePlan.setId(id);
        purchasePlan.setPurchaseSku(purchaseSku);
        purchasePlan.setSupplierName(supplierName);
        purchasePlan.setProductId(productId);
        purchasePlan.setVariantId(variantId);
        purchasePlan.setVariantSku(variantSku);
        purchasePlan.setCnName(cnName);
        purchasePlan.setVariantImage(variantImage);
        purchasePlan.setPurchaseLink(purchaseLink);
        purchasePlan.setQuantity(quantity);
        purchasePlan.setSpecId(specId);
        purchasePlan.setOperatorId(operatorId);
        purchasePlan.setState(state);
        purchasePlan.setPurchaseOrderId(purchaseOrderId);
        purchasePlan.setCreateTime(createTime);
        purchasePlan.setUpdateTime(updateTime);
        return purchasePlan;
    }

}
