package com.upedge.pms.modules.quote.request;

import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class CustomerProductQuoteUpdateRequest{

    /**
     * 
     */
    @NotNull
    private Long storeVariantId;
    /**
     * 
     */
    @NotNull
    private String variantSku;
    /**
     * 
     */
    @NotNull
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
