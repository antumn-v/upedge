package com.upedge.oms.modules.vat.request;

import com.upedge.oms.modules.vat.entity.CustomerVatRule;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerVatRuleAddRequest{

    /**
    * 
    */
    private Long vatRuleId;

    public CustomerVatRule toCustomerVatRule(){
        CustomerVatRule customerVatRule=new CustomerVatRule();
        customerVatRule.setVatRuleId(vatRuleId);
        return customerVatRule;
    }

}
