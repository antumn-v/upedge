package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.ProductQuoteRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class ProductQuoteRecordUpdateRequest{

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

    public ProductQuoteRecord toProductQuoteRecord(Integer id){
        ProductQuoteRecord productQuoteRecord=new ProductQuoteRecord();
        productQuoteRecord.setId(id);
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
