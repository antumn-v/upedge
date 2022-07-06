package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductPurchaseInfoUpdateRequest{

    /**
     * 
     */
    private String purchaseLink;
    /**
     * 
     */
    private String supplierName;
    /**
     * 
     */
    private String specId;

    public ProductPurchaseInfo toProductPurchaseInfo(String purchaseSku){
        ProductPurchaseInfo productPurchaseInfo=new ProductPurchaseInfo();
        productPurchaseInfo.setPurchaseSku(purchaseSku);
        productPurchaseInfo.setPurchaseLink(purchaseLink);
        productPurchaseInfo.setSupplierName(supplierName);
        productPurchaseInfo.setSpecId(specId);
        return productPurchaseInfo;
    }

}
