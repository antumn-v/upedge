package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.entity.CustomerVatRule;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerVatRuleUpdateRequest{

    /**
     * 
     */
    private Long vatRuleId;

    public CustomerVatRule toCustomerVatRule(Long customerId){
        CustomerVatRule customerVatRule=new CustomerVatRule();
        customerVatRule.setCustomerId(customerId);
        customerVatRule.setVatRuleId(vatRuleId);
        return customerVatRule;
    }

}
