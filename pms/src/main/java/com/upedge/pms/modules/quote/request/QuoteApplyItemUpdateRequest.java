package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class QuoteApplyItemUpdateRequest{

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
    private Long storeProductId;
    /**
     * 
     */
    private Long storeVariantId;
    /**
     * 
     */
    private String storeVariantName;
    /**
     * 
     */
    private String storeProductTitle;
    /**
     * 
     */
    private String storeProductSku;
    /**
     * 
     */
    private String storeVariantImage;
    /**
     * 
     */
    private String productTitle;
    /**
     * 
     */
    private String variantName;
    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private String variantImage;
    /**
     * 
     */
    private BigDecimal quotePrice;
    /**
     * 
     */
    private Long quoteApplyId;

    public QuoteApplyItem toQuoteApplyItem(Long id){
        QuoteApplyItem quoteApplyItem=new QuoteApplyItem();
        quoteApplyItem.setId(id);
        quoteApplyItem.setProductId(productId);
        quoteApplyItem.setVariantId(variantId);
        quoteApplyItem.setStoreProductId(storeProductId);
        quoteApplyItem.setStoreVariantId(storeVariantId);
        quoteApplyItem.setStoreVariantName(storeVariantName);
        quoteApplyItem.setStoreProductTitle(storeProductTitle);
        quoteApplyItem.setStoreProductSku(storeProductSku);
        quoteApplyItem.setStoreVariantImage(storeVariantImage);
        quoteApplyItem.setProductTitle(productTitle);
        quoteApplyItem.setVariantName(variantName);
        quoteApplyItem.setVariantSku(variantSku);
        quoteApplyItem.setVariantImage(variantImage);
        quoteApplyItem.setQuotePrice(quotePrice);
        quoteApplyItem.setQuoteApplyId(quoteApplyId);
        return quoteApplyItem;
    }

}
