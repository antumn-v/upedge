package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
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
    private String variantSku;
    /**
     * 
     */
    private BigDecimal quotePrice;

    public CustomerProductQuote toCustomerProductQuote(Long customerId){
        CustomerProductQuote customerProductQuote=new CustomerProductQuote();
        customerProductQuote.setCustomerId(customerId);
        customerProductQuote.setStoreVariantId(storeVariantId);
        customerProductQuote.setVariantSku(variantSku);
        customerProductQuote.setQuotePrice(quotePrice);
        return customerProductQuote;
    }

}
