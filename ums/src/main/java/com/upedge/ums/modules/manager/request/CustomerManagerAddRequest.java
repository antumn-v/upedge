package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.CustomerManager;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerManagerAddRequest{

    /**
    * 用户经理code
    */
    private String managerCode;
    /**
    * 
    */
    private Date createTime;

    public CustomerManager toCustomerManager(){
        CustomerManager customerManager=new CustomerManager();
        customerManager.setManagerCode(managerCode);
        customerManager.setCreateTime(createTime);
        return customerManager;
    }

}
