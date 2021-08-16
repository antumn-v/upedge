package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.CustomerApplication;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerApplicationUpdateRequest{


    Long customerId;
    /**
     * 
     */
    private Long applicationId;

    public CustomerApplication toCustomerApplication(){
        CustomerApplication customerApplication=new CustomerApplication();
        customerApplication.setCustomerId(customerId);
        customerApplication.setApplicationId(applicationId);
        return customerApplication;
    }

}
