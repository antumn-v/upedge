package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.ProductAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductAttrAddRequest{

    /**
    * 
    */
    private Long productId;
    /**
    * 
    */
    private String attrKey;
    /**
    * 
    */
    private String attrValue;

    public ProductAttr toProductAttr(){
        ProductAttr productAttr=new ProductAttr();
        productAttr.setProductId(productId);
        productAttr.setAttrKey(attrKey);
        productAttr.setAttrValue(attrValue);
        return productAttr;
    }

}
