package com.upedge.ums.modules.manager.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerManagerAddRequest{

    /**
    * 用户经理code
    */
    private Long managerId;

    public CustomerManager toCustomerManager(){
        CustomerManager customerManager=new CustomerManager();
        customerManager.setManagerId(managerId);
        return customerManager;
    }

}
