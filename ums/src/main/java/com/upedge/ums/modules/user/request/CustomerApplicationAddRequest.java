package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerApplication;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerApplicationAddRequest{

    /**
    * 
    */
    private Long applicationId;

    public CustomerApplication toCustomerApplication(){
        CustomerApplication customerApplication=new CustomerApplication();
        customerApplication.setApplicationId(applicationId);
        return customerApplication;
    }

}
