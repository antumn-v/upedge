package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.QuoteApplyItem;
import lombok.Data;

import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class QuoteApplyItemAddRequest{

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
    private String storeVariantSku;
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

    public QuoteApplyItem toQuoteApplyItem(){
        QuoteApplyItem quoteApplyItem=new QuoteApplyItem();
        quoteApplyItem.setProductId(productId);
        quoteApplyItem.setVariantId(variantId);
        quoteApplyItem.setStoreProductId(storeProductId);
        quoteApplyItem.setStoreVariantId(storeVariantId);
        quoteApplyItem.setStoreVariantName(storeVariantName);
        quoteApplyItem.setStoreProductTitle(storeProductTitle);
        quoteApplyItem.setStoreVariantSku(storeVariantSku);
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
