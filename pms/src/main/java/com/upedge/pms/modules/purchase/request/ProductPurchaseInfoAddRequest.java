package com.upedge.pms.modules.purchase.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductPurchaseInfoAddRequest{

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

    public ProductPurchaseInfo toProductPurchaseInfo(){
        ProductPurchaseInfo productPurchaseInfo=new ProductPurchaseInfo();
        productPurchaseInfo.setPurchaseLink(purchaseLink);
        productPurchaseInfo.setSupplierName(supplierName);
        productPurchaseInfo.setSpecId(specId);
        return productPurchaseInfo;
    }

}
