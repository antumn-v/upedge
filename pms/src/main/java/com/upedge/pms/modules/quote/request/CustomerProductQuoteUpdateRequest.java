package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class CustomerProductQuoteUpdateRequest{

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
    private Long productId;
    /**
     * 
     */
    private Long variantId;
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

    public CustomerProductQuote toCustomerProductQuote(Long customerId){
        CustomerProductQuote customerProductQuote=new CustomerProductQuote();
        customerProductQuote.setCustomerId(customerId);
        customerProductQuote.setStoreVariantId(storeVariantId);
        customerProductQuote.setStoreProductId(storeProductId);
        customerProductQuote.setProductId(productId);
        customerProductQuote.setVariantId(variantId);
        customerProductQuote.setStoreVariantName(storeVariantName);
        customerProductQuote.setStoreProductTitle(storeProductTitle);
        customerProductQuote.setStoreProductSku(storeProductSku);
        customerProductQuote.setStoreVariantImage(storeVariantImage);
        customerProductQuote.setProductTitle(productTitle);
        customerProductQuote.setVariantName(variantName);
        customerProductQuote.setVariantSku(variantSku);
        customerProductQuote.setVariantImage(variantImage);
        customerProductQuote.setQuotePrice(quotePrice);
        customerProductQuote.setQuoteApplyId(quoteApplyId);
        return customerProductQuote;
    }

}
