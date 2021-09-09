package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ProductVariantAttrUpdateRequest{

    /**
     * 中文名称
     */
    private String variantAttrCname;
    /**
     * 
     */
    private String variantAttrEname;
    /**
     * 
     */
    private String originalAttrCvalue;
    /**
     * 中文值
     */
    private String variantAttrCvalue;
    /**
     * 
     */
    private String variantAttrEvalue;
    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private Long productId;
    /**
     * 
     */
    private Integer seq;

    public ProductVariantAttr toProductVariantAttr(Long id){
        ProductVariantAttr productVariantAttr=new ProductVariantAttr();
        productVariantAttr.setId(id);
        productVariantAttr.setVariantAttrCname(variantAttrCname);
        productVariantAttr.setVariantAttrEname(variantAttrEname);
        productVariantAttr.setOriginalAttrCvalue(originalAttrCvalue);
        productVariantAttr.setVariantAttrCvalue(variantAttrCvalue);
        productVariantAttr.setVariantAttrEvalue(variantAttrEvalue);
        productVariantAttr.setVariantId(variantId);
        productVariantAttr.setProductId(productId);
        productVariantAttr.setSeq(seq);
        return productVariantAttr;
    }

}
