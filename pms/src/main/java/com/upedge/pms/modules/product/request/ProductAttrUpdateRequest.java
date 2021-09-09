package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductAttrUpdateRequest{

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

    public ProductAttr toProductAttr(Long id){
        ProductAttr productAttr=new ProductAttr();
        productAttr.setId(id);
        productAttr.setProductId(productId);
        productAttr.setAttrKey(attrKey);
        productAttr.setAttrValue(attrValue);
        return productAttr;
    }

}
