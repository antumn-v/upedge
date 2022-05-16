package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.CustomerManager;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerManagerUpdateRequest{

    /**
     * 用户经理code
     */
    private Long managerId;

    public CustomerManager toCustomerManager(Long id){
        CustomerManager customerManager=new CustomerManager();
        customerManager.setManagerId(managerId);
        return customerManager;
    }

}
