package com.upedge.pms.modules.quote.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ProductQuoteRecordAddRequest{

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
    private Long storeVariantId;
    /**
    * 
    */
    private Long storeProductId;
    /**
    * 
    */
    private BigDecimal quotePrice;
    /**
    * 
    */
    private Long userId;
    /**
    * 
    */
    private Date createTime;

    public ProductQuoteRecord toProductQuoteRecord(){
        ProductQuoteRecord productQuoteRecord=new ProductQuoteRecord();
        productQuoteRecord.setProductId(productId);
        productQuoteRecord.setVariantId(variantId);
        productQuoteRecord.setStoreVariantId(storeVariantId);
        productQuoteRecord.setStoreProductId(storeProductId);
        productQuoteRecord.setQuotePrice(quotePrice);
        productQuoteRecord.setUserId(userId);
        productQuoteRecord.setCreateTime(createTime);
        return productQuoteRecord;
    }

}
