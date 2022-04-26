package com.upedge.sms.modules.photography.request;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.photography.entity.ProductPhotographyOrderItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ProductPhotographyOrderItemAddRequest{

    /**
    * 
    */
    private Long orderId;
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
    private String productTitle;
    /**
    * 
    */
    private String variantSku;
    /**
    * 
    */
    private String variantName;
    /**
    * 
    */
    private String variantImage;
    /**
    * 
    */
    private BigDecimal price;

    public ProductPhotographyOrderItem toProductPhotographyOrderItem(){
        ProductPhotographyOrderItem productPhotographyOrderItem=new ProductPhotographyOrderItem();
        productPhotographyOrderItem.setOrderId(orderId);
        productPhotographyOrderItem.setVariantId(variantId);
        productPhotographyOrderItem.setProductId(productId);
        productPhotographyOrderItem.setProductTitle(productTitle);
        productPhotographyOrderItem.setVariantSku(variantSku);
        productPhotographyOrderItem.setVariantName(variantName);
        productPhotographyOrderItem.setVariantImage(variantImage);
        productPhotographyOrderItem.setPrice(price);
        return productPhotographyOrderItem;
    }

}
